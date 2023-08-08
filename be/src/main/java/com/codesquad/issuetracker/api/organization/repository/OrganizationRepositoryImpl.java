package com.codesquad.issuetracker.api.organization.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final NamedParameterJdbcTemplate template;

    public Optional<Long> findBy(String organizationTitle) {
        String sql = "SELECT id "
                + "FROM organization "
                + "WHERE title = :organizationTitle";
        List<Long> ids = template.query(sql, Collections.singletonMap("organizationTitle", organizationTitle),
            (rs, rowNum) -> rs.getLong(1));
        return ids.stream().findFirst();
    }

}
