package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
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
    public static final String FIND_BY_ID_SQL =
            "SELECT id,title,description,due_date,is_closed,organization_id"
                    + " FROM milestone"
                    + " WHERE id = :id";
    public static final String UPDATE_SQL =
            "UPDATE milestone"
                    + " SET title = :title,description = :description ,due_date = :due_date,is_closed = :is_closed"
                    + " WHERE id = :id";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Long> save(Milestone milestone) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TITLE, milestone.getTitle())
                .addValue(DESCRIPTION, milestone.getDescription())
                .addValue(DUE_DATE, Date.valueOf(milestone.getDueDate()))
                .addValue(IS_CLOSED, milestone.isClosed())
                .addValue(ORGANIZATION_ID, milestone.getOrganizationId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SAVE_SQL, sqlParameterSource, keyHolder);
        Number key = keyHolder.getKey();
        return Optional.ofNullable(key).map(Number::longValue);
    }

    @Override
    public Optional<Milestone> findById(Long milestoneId) {
        Milestone milestone = DataAccessUtils.singleResult(
                jdbcTemplate.query(FIND_BY_ID_SQL, Map.of(ID, milestoneId), (rs, rowNum) ->
                        Milestone.builder()
                                .id(rs.getLong(ID))
                                .title(rs.getString(TITLE))
                                .description(rs.getString(DESCRIPTION))
                                .dueDate(rs.getDate(DUE_DATE).toLocalDate())
                                .organizationId(rs.getLong(ORGANIZATION_ID))
                                .isClosed(rs.getBoolean(IS_CLOSED))
                                .build()
                ));
        return Optional.ofNullable(milestone);
    }

    @Override
    public void update(Milestone milestone) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TITLE, milestone.getTitle())
                .addValue(DESCRIPTION, milestone.getDescription())
                .addValue(DUE_DATE, Date.valueOf(milestone.getDueDate()))
                .addValue(IS_CLOSED, milestone.isClosed())
                .addValue(ID, milestone.getId());
        jdbcTemplate.update(UPDATE_SQL, sqlParameterSource);
    }
}
