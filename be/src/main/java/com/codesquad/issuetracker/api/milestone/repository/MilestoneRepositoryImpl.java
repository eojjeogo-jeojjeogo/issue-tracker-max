package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MilestoneRepositoryImpl implements MilestoneRepository {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DUE_DATE = "due_date";
    private static final String IS_CLOSED = "is_closed";
    private static final String ORGANIZATION_ID = "organization_id";
    private static final String ID = "id";
    private static final String ISSUE_OPENED_COUNT = "issueOpenedCount";
    private static final String ISSUE_CLOSED_COUNT = "issueClosedCount";
    private static final String MILESTONE_ID = "milestoneId";
    private static final String SAVE_SQL =
        "INSERT INTO milestone (title,description,due_date,is_closed,organization_id)"
            + " values (:title,:description,:due_date,:is_closed,:organization_id)";
    private static final String UPDATE_SQL =
        "UPDATE milestone"
            + " SET title = :title,description = :description ,due_date = :due_date,is_closed = :is_closed"
            + " WHERE id = :id";
    public static final String DELETE_SQL = "DELETE FROM milestone WHERE id = :id";
    private static final String UPDATE_STATUS_SQL =
        "UPDATE milestone"
            + " SET is_closed = :is_closed"
            + " WHERE id = :id";
    private static final String FIND_FILTER_BY_ORGANIZATION_ID_SQL =
        "SELECT id,title"
            + " from milestone"
            + " WHERE organization_id = :organization_id WHERE is_closed = false";
    private static final String FIND_COUNT_BY_ORGANIZATION_SQL =
        "SELECT COUNT(id)"
            + " FROM milestone"
            + " WHERE organization_id = :organization_id AND is_closed = false";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Long> save(Milestone milestone) {
        SqlParameterSource sqlParameterSource = getSaveSqlParameterSource(milestone);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SAVE_SQL, sqlParameterSource, keyHolder);
        Number key = keyHolder.getKey();
        return Optional.ofNullable(key).map(Number::longValue);
    }

    @Override
    public Optional<MilestoneVo> findById(Long milestoneId) {
        String sql = "SELECT m.id ,m.title, "
            + "SUM(CASE WHEN i.is_closed = false THEN 1 ELSE 0 END) AS issueOpenedCount, "
            + "SUM(CASE WHEN i.is_closed = true THEN 1 ELSE 0 END) AS issueClosedCount "
            + "FROM milestone m "
            + "LEFT JOIN issue i ON m.id = i.milestone_id "
            + "WHERE m.is_closed = FALSE AND m.id = :milestoneId "
            + "GROUP BY m.id ";

        return jdbcTemplate.query(sql,
            Collections.singletonMap(MILESTONE_ID, milestoneId),
            milestoneVoRowMapper()).stream().findFirst();
    }

    private RowMapper<MilestoneVo> milestoneVoRowMapper() {
        return (rs, rowNum) ->
            MilestoneVo.builder()
                .id(rs.getLong(ID))
                .title(rs.getString(TITLE))
                .issueOpenedCount(rs.getInt(ISSUE_OPENED_COUNT))
                .issueClosedCount(rs.getInt(ISSUE_CLOSED_COUNT))
                .build();
    }

    @Override
    public void update(Milestone milestone) {
        SqlParameterSource sqlParameterSource = getUpdateSqlParameterSource(milestone);
        jdbcTemplate.update(UPDATE_SQL, sqlParameterSource);
    }

    @Override
    public void deleteById(Long milestoneId) {
        jdbcTemplate.update(DELETE_SQL, Map.of(ID, milestoneId));
    }

    @Override
    public List<MilestonesVo> readAllByOrganizationId(Long organizationId) {
        String sql =
            "SELECT m.id ,m.title, m.description, m.due_date, m.is_closed, "
                + "SUM(CASE WHEN i.is_closed = false THEN 1 ELSE 0 END) AS issueOpenedCount, "
                + "SUM(CASE WHEN i.is_closed = true THEN 1 ELSE 0 END) AS issueClosedCount "
                + "FROM milestone m "
                + "LEFT JOIN issue i ON m.id = i.milestone_id "
                + "WHERE m.organization_id = :organization_id "
                + "GROUP BY m.id ";

        return jdbcTemplate.query(sql, Collections.singletonMap(ORGANIZATION_ID, organizationId),
            milestonesVoRowMapper());
    }

    private RowMapper<MilestonesVo> milestonesVoRowMapper() {
        return (rs, rowNum) ->
            MilestonesVo.builder()
                .id(rs.getLong(ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .dueDate(rs.getTimestamp(DUE_DATE).toLocalDateTime().toLocalDate())
                .isClosed(rs.getBoolean(IS_CLOSED))
                .issueOpenedCount(rs.getInt(ISSUE_OPENED_COUNT))
                .issueClosedCount(rs.getInt(ISSUE_CLOSED_COUNT))
                .build();
    }

    @Override
    public void updateStatus(Long milestoneId, boolean isClosed) {
        jdbcTemplate.update(UPDATE_STATUS_SQL, Map.of(IS_CLOSED, isClosed, ID, milestoneId));
    }

    @Override
    public List<MilestoneFilter> findFilterByOrganizationId(Long organizationId) {

        return jdbcTemplate.query(FIND_FILTER_BY_ORGANIZATION_ID_SQL,
            Map.of(ORGANIZATION_ID, organizationId),
            getMilestoneFilterRowMapper());
    }

    @Override
    public Long findCountByOrganizationId(Long organizationId) {
        return jdbcTemplate.queryForObject(
            FIND_COUNT_BY_ORGANIZATION_SQL,
            Map.of(ORGANIZATION_ID, organizationId),
            Long.class
        );
    }

    private static MapSqlParameterSource getSaveSqlParameterSource(Milestone milestone) {
        return new MapSqlParameterSource()
            .addValue(TITLE, milestone.getTitle())
            .addValue(DESCRIPTION, milestone.getDescription())
            .addValue(DUE_DATE, Date.valueOf(milestone.getDueDate()))
            .addValue(IS_CLOSED, milestone.isClosed())
            .addValue(ORGANIZATION_ID, milestone.getOrganizationId());
    }

    private static MapSqlParameterSource getUpdateSqlParameterSource(Milestone milestone) {
        return new MapSqlParameterSource()
            .addValue(TITLE, milestone.getTitle())
            .addValue(DESCRIPTION, milestone.getDescription())
            .addValue(DUE_DATE, Date.valueOf(milestone.getDueDate()))
            .addValue(IS_CLOSED, milestone.isClosed())
            .addValue(ID, milestone.getId());
    }

    private static RowMapper<MilestoneFilter> getMilestoneFilterRowMapper() {
        return (rs, rowNum) -> new MilestoneFilter(rs.getLong(ID), rs.getString(TITLE));
    }
}
