package com.codesquad.issuetracker.api.filter.service;

import com.codesquad.issuetracker.api.filter.dto.DynamicFiltersResponse;
import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.issue.repository.IssueAssigneeRepository;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestoneResponse;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.OrganizationException;
import java.util.List;
import java.util.stream.Collectors;
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
    private final IssueAssigneeRepository issueAssigneeRepository;

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

    @Transactional(readOnly = true)
    public DynamicFiltersResponse readDynamicFilters(String organizationTitle) {
        Long organizationId = getOrganizationId(organizationTitle);

        // TODO: 기존 dto가 있어 우선 사용했는데 리랙토링 필요할 듯
        List<IssueAssigneeVo> assignees = issueAssigneeRepository.findAllByOrganizationId(organizationId);
        List<LabelFilter> labels = labelRepository.findFiltersBy(organizationId);
        List<MilestoneResponse> milestones = milestoneRepository.findAllBy(organizationId).stream()
                .map(milestonesVo -> new MilestoneResponse(milestonesVo.getId(), milestonesVo.getTitle()))
                .collect(Collectors.toList());
        List<MemberFilter> authors = memberRepository.findFiltersBy(organizationId);

        return new DynamicFiltersResponse(assignees, labels, milestones, authors);
    }

      private Long getOrganizationId(String organizationTitle) {
        return organizationRepository.findBy(organizationTitle)
                .orElseThrow(() -> new CustomRuntimeException(
                        OrganizationException.ORGANIZATION_NOT_FOUND_EXCEPTION));
    }
}
