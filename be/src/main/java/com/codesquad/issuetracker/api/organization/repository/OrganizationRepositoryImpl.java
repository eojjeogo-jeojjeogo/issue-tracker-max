package com.codesquad.issuetracker.api.organization.repository;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long findIdByTitle(String title) {
        String findByTitleSql = "SELECT (id) FROM organization WHERE title = :title";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of("title", title)
        );
        return DataAccessUtils.singleResult(jdbcTemplate.query(findByTitleSql, sqlParameterSource, (rs, rowNum) ->
                rs.getLong("id")));
    }
}
