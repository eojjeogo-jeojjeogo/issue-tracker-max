package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestonesResponse;
import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.service.OrganizationService;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.MilestoneException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationService organizationService;

    @Transactional
    public long create(String organizationTitle, MilestoneRequest mileStoneRequest) {
        Long organizationId = organizationService.getOrganizationIdBy(organizationTitle);
        Milestone milestone = mileStoneRequest.toEntityByOrganizationId(organizationId);
        return milestoneRepository.save(milestone)
                .orElseThrow(() -> new CustomRuntimeException(MilestoneException.MILESTONE_SAVE_FAIL_EXCEPTION));
    }

    public MilestoneVo read(Long milestoneId) {
        if (milestoneId == 0) {
            return null;
        }
        return milestoneRepository.findBy(milestoneId)
                .orElseThrow(() -> new CustomRuntimeException(MilestoneException.MILESTONE_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public MilestonesResponse readAll(String organizationTitle, FilterStatus filterStatus) {
        Long organizationId = organizationService.getOrganizationIdBy(organizationTitle);
        List<MilestonesVo> milestones = milestoneRepository.findAllBy(organizationId);
        return MilestonesResponse.from(milestones, filterStatus);
    }

    public long update(Long milestoneId, MilestoneRequest mileStoneRequest) {
        Milestone milestone = mileStoneRequest.toEntityByMilestoneId(milestoneId);
        milestoneRepository.update(milestone);
        return milestoneId;
    }

    public void updateStatus(Long milestoneId, Boolean isClosed) {
        milestoneRepository.update(milestoneId, isClosed);
    }

    public void delete(Long milestoneId) {
        milestoneRepository.delete(milestoneId);
    }

}
