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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Long> save(Milestone milestone) {
        String saveSQL = "INSERT INTO milestone (title,description,due_date,is_closed,organization_id)"
                + " values (:title,:description,:dueDate,:isClosed,:organizationId)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of("title", milestone.getTitle(),
                        "description", milestone.getDescription(),
                        "dueDate", Date.valueOf(milestone.getDueDate()),
                        "isClosed", milestone.isClosed(),
                        "organizationId", milestone.getOrganizationId())
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(saveSQL, sqlParameterSource, keyHolder);
        Number key = keyHolder.getKey();
        return Optional.ofNullable(key).map(Number::longValue);
    }

    @Override
    public Optional<Milestone> findById(Long milestoneId) {
        String findByIdSQL = "SELECT id,title,description,due_date,is_closed FROM milestone WHERE id = :id";

        Milestone milestone = DataAccessUtils.singleResult(
                jdbcTemplate.query(findByIdSQL, Map.of("id", milestoneId), (rs, rowNum) ->
                        Milestone.builder()
                                .id(rs.getLong("id"))
                                .title(rs.getString("title"))
                                .description(rs.getString("description"))
                                .dueDate(rs.getDate("due_date").toLocalDate())
                                .isClosed(rs.getBoolean("is_closed"))
                                .build()
                ));
        return Optional.ofNullable(milestone);
    }
}
