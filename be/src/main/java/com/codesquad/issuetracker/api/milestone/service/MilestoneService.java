package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneCreateRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MileStoneResponse;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public long create(String organizationTitle, MilestoneCreateRequest mileStoneCreateRequest) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        Milestone milestone = MilestoneCreateRequest.toEntity(mileStoneCreateRequest, organizationId);
        return milestoneRepository.save(milestone)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public MileStoneResponse read(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow();
        return MileStoneResponse.toEntity(milestone);
    }
}
