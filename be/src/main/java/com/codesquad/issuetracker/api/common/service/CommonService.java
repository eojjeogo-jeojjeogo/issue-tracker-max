package com.codesquad.issuetracker.api.common.service;

import com.codesquad.issuetracker.api.common.dto.NavigationResponse;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommonService {

    private final OrganizationRepository organizationRepository;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;

    @Transactional(readOnly = true)
    public NavigationResponse getNavigationInfo(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        Long labelsCount = labelRepository.countBy(organizationId);
        Long milestonesCount = milestoneRepository.countBy(organizationId);
        return new NavigationResponse(labelsCount, milestonesCount);
    }
}
