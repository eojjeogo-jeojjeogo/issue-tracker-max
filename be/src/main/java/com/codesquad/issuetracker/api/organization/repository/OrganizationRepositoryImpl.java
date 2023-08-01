package com.codesquad.issuetracker.api.organization.repository;

import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository{

    private final NamedParameterJdbcTemplate template;

    public OrganizationRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Optional<Long> findIdByTitle(String organizationTitle){
        String sql = "SELECT id FROM organization WHERE title = :organizationTitle";
        Long id = template.queryForObject(sql, Map.of("organizationTitle", organizationTitle), Long.class);
        return Optional.ofNullable(id);
    }

}
