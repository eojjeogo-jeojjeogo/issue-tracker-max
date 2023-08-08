package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestonesResponse;
import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public long create(String organizationTitle, MilestoneRequest mileStoneRequest) {
        Long organizationId = organizationRepository.findBy(organizationTitle)
            .orElseThrow();
        Milestone milestone = MilestoneRequest.toEntity(mileStoneRequest, organizationId);
        return milestoneRepository.save(milestone)
            .orElseThrow();
    }

    @Transactional(readOnly = true)
    public MilestoneVo read(Long milestoneId) {
        MilestoneVo milestone = milestoneRepository.findBy(milestoneId).orElseThrow();
        return milestone;
    }

    @Transactional
    public long update(Long milestoneId, MilestoneRequest mileStoneRequest) {
        Milestone milestone = MilestoneRequest.toEntity(milestoneId, mileStoneRequest);
        milestoneRepository.update(milestone);
        return milestoneId;
    }

    @Transactional
    public void delete(Long milestoneId) {
        milestoneRepository.delete(milestoneId);
    }

    @Transactional
    public MilestonesResponse readAll(String organizationTitle, FilterStatus filterStatus) {
        Long organizationId = organizationRepository.findBy(organizationTitle)
            .orElseThrow();
        List<MilestonesVo> milestones = milestoneRepository.findAllBy(organizationId);
        return MilestonesResponse.from(milestones, filterStatus);
    }

    @Transactional
    public void updateStatus(Long milestoneId, boolean isClosed) {
        milestoneRepository.update(milestoneId, isClosed);
    }
}
