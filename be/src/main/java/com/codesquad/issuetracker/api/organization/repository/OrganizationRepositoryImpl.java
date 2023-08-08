package com.codesquad.issuetracker.api.organization.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final NamedParameterJdbcTemplate template;

    public OrganizationRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

}
