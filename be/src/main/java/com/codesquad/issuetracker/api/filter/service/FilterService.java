package com.codesquad.issuetracker.api.filter.service;

import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.OrganizationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FilterService {

    private final OrganizationRepository organizationRepository;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MilestoneFilter> readMilestones(String organizationTitle) {
        Long organizationId = getOrganizationId(organizationTitle);
        return milestoneRepository.findFiltersBy(organizationId);
    }

    @Transactional(readOnly = true)
    public List<LabelFilter> readLabels(String organizationTitle) {
        Long organizationId = getOrganizationId(organizationTitle);
        return labelRepository.findFiltersBy(organizationId);
    }

    @Transactional(readOnly = true)
    public List<MemberFilter> readAssignees(String organizationTitle) {
        Long organizationId = getOrganizationId(organizationTitle);
        return memberRepository.findFiltersBy(organizationId);
    }

    private Long getOrganizationId(String organizationTitle) {
        return organizationRepository.findBy(organizationTitle)
                .orElseThrow(() -> new CustomRuntimeException(
                        OrganizationException.ORGANIZATION_NOT_FOUND_EXCEPTION));
    }
}
