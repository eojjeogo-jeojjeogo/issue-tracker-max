package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
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
    private static final String SAVE_SQL =
            "INSERT INTO milestone (title,description,due_date,is_closed,organization_id)"
                    + " values (:title,:description,:due_date,:is_closed,:organization_id)";
    private static final String FIND_BY_ID_SQL =
            "SELECT id,title,description,due_date,is_closed,organization_id"
                    + " FROM milestone"
                    + " WHERE id = :id";
    private static final String UPDATE_SQL =
            "UPDATE milestone"
                    + " SET title = :title,description = :description ,due_date = :due_date,is_closed = :is_closed"
                    + " WHERE id = :id";
    private static final String FIND_ALL_BY_ORGANIZATION_ID_SQL =
            "SELECT id,title,description,due_date,is_closed,organization_id"
                    + " FROM milestone"
                    + " WHERE organization_id = :organization_id";
    private static final String DELETE_SQL = "DELETE FROM milestone WHERE id = :id";
    private static final String UPDATE_STATUS_SQL =
            "UPDATE milestone"
                    + " SET is_closed = :is_closed"
                    + " WHERE id = :id";
    private static final String FIND_COUNT_BY_ORGANIZATION_SQL =
            "SELECT COUNT(id)"
            + " FROM milestone"
            + " WHERE organization_id = :organization_id";
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
    public Optional<Milestone> findById(Long milestoneId) {
        Milestone milestone = DataAccessUtils.singleResult(
                jdbcTemplate.query(FIND_BY_ID_SQL, Map.of(ID, milestoneId), getMilestoneRowMapper()
                ));
        return Optional.ofNullable(milestone);
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
    public List<Milestone> readAllByOrganizationId(Long organizationId) {
        return jdbcTemplate.query(FIND_ALL_BY_ORGANIZATION_ID_SQL,
                Map.of(ORGANIZATION_ID, organizationId),
                getMilestoneRowMapper());
    }

    @Override
    public void updateStatus(Long milestoneId, boolean isClosed) {
        jdbcTemplate.update(UPDATE_STATUS_SQL, Map.of(IS_CLOSED, isClosed, ID, milestoneId));
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

    private static RowMapper<Milestone> getMilestoneRowMapper() {
        return (rs, rowNum) ->
                Milestone.builder()
                        .id(rs.getLong(ID))
                        .title(rs.getString(TITLE))
                        .description(rs.getString(DESCRIPTION))
                        .dueDate(rs.getDate(DUE_DATE).toLocalDate())
                        .organizationId(rs.getLong(ORGANIZATION_ID))
                        .isClosed(rs.getBoolean(IS_CLOSED))
                        .build();
    }
}
