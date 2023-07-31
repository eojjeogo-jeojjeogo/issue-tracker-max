package com.codesquad.issuetracker.api.organization.repository;

public interface OrganizationRepository {

    Long findIdByTitle(String title);

}
