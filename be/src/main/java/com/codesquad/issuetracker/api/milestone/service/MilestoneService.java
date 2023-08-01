package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.dto.MilestoneCreateRequest;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationRepository organizationRepository;

    public long create(String organizationTitle, MilestoneCreateRequest mileStoneCreateRequest) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        Milestone milestone = MilestoneCreateRequest.toEntity(mileStoneCreateRequest, organizationId);
        return milestoneRepository.save(milestone)
                .orElseThrow();
    }
}
