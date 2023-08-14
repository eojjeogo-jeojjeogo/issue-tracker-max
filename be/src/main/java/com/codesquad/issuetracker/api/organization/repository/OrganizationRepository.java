package com.codesquad.issuetracker.api.organization.repository;

import java.util.Optional;

public interface OrganizationRepository {

    Optional<Long> findBy(String organizationTitle);

}
