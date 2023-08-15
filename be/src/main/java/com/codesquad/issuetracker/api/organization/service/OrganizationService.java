package com.codesquad.issuetracker.api.organization.service;

import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.OrganizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Long getOrganizationIdBy(String organizationTitle) {
        return organizationRepository.findBy(organizationTitle)
                .orElseThrow(() -> new CustomRuntimeException(
                        OrganizationException.ORGANIZATION_NOT_FOUND_EXCEPTION));
    }
}
