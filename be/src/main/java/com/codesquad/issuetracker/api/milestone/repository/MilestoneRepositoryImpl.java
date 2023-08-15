package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
    private static final String ID = "id";
    private static final String ISSUE_OPENED_COUNT = "issueOpenedCount";
    private static final String ISSUE_CLOSED_COUNT = "issueClosedCount";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Long> save(Milestone milestone) {
        String sql = "INSERT INTO milestone (title, description, due_date, is_closed, organization_id)"
                + " VALUES (:title, :description, :dueDate, :isClosed, :organizationId)";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(milestone);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        Number key = keyHolder.getKey();
        return Optional.ofNullable(key).map(Number::longValue);
    }

    @Override
    public Optional<MilestoneVo> findBy(Long milestoneId) {
        String sql = "SELECT m.id ,m.title, "
                + "SUM(CASE WHEN i.is_closed = false THEN 1 ELSE 0 END) AS issueOpenedCount, "
                + "SUM(CASE WHEN i.is_closed = true THEN 1 ELSE 0 END) AS issueClosedCount "
                + "FROM milestone m "
                + "LEFT JOIN issue i ON m.id = i.milestone_id "
                + "WHERE m.is_closed = FALSE AND m.id = :milestoneId "
                + "GROUP BY m.id ";

        return jdbcTemplate.query(sql, Collections.singletonMap("milestoneId", milestoneId), milestoneVoRowMapper())
                .stream()
                .findFirst();
    }

    @Override
    public void update(Milestone milestone) {
        String sql = "UPDATE milestone"
                + " SET title = :title,description = :description ,due_date = :dueDate,is_closed = :isClosed"
                + " WHERE id = :id";

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(milestone);
        jdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public void delete(Long milestoneId) {
        String sql = "DELETE FROM milestone WHERE id = :id";

        jdbcTemplate.update(sql, Collections.singletonMap("id", milestoneId));
    }

    @Override
    public List<MilestonesVo> findAllBy(Long organizationId) {
        String sql = "SELECT m.id ,m.title, m.description, m.due_date, m.is_closed, "
                + "SUM(CASE WHEN i.is_closed = false THEN 1 ELSE 0 END) AS issueOpenedCount, "
                + "SUM(CASE WHEN i.is_closed = true THEN 1 ELSE 0 END) AS issueClosedCount "
                + "FROM milestone m "
                + "LEFT JOIN issue i ON m.id = i.milestone_id "
                + "WHERE m.organization_id = :organizationId "
                + "GROUP BY m.id ";

        return jdbcTemplate.query(
                sql,
                Collections.singletonMap("organizationId", organizationId),
                milestonesVoRowMapper()
        );
    }

    @Override
    public void update(Long milestoneId, boolean isClosed) {
        String sql = "UPDATE milestone "
                + "SET is_closed = :isClosed "
                + "WHERE id = :id";

        jdbcTemplate.update(sql, Map.of("isClosed", isClosed, "id", milestoneId));
    }

    @Override
    public List<MilestoneFilter> findFiltersBy(Long organizationId) {
        String sql = "SELECT id,title "
                + "FROM milestone "
                + "WHERE organization_id = :organizationId AND is_closed = false";

        return jdbcTemplate.query(
                sql,
                Collections.singletonMap("organizationId", organizationId),
                milestoneFilterRowMapper()
        );
    }

    @Override
    public Long countBy(Long organizationId) {
        String sql = "SELECT COUNT(id) "
                + "FROM milestone "
                + "WHERE organization_id = :organizationId AND is_closed = false";
        return jdbcTemplate.queryForObject(
                sql,
                Collections.singletonMap("organizationId", organizationId),
                Long.class);
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

    private RowMapper<MilestoneVo> milestoneVoRowMapper() {
        return (rs, rowNum) ->
                MilestoneVo.builder()
                        .id(rs.getLong(ID))
                        .title(rs.getString(TITLE))
                        .issueOpenedCount(rs.getInt(ISSUE_OPENED_COUNT))
                        .issueClosedCount(rs.getInt(ISSUE_CLOSED_COUNT))
                        .build();
    }

    private RowMapper<MilestoneFilter> milestoneFilterRowMapper() {
        return (rs, rowNum) -> MilestoneFilter.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(TITLE))
                .build();
    }
}
