package com.codesquad.issuetracker.api.milestone.service;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MileStoneResponse;
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
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        Milestone milestone = MilestoneRequest.toEntity(mileStoneRequest, organizationId);
        return milestoneRepository.save(milestone)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public MileStoneResponse read(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow();
        return MileStoneResponse.toEntity(milestone);
    }

    @Transactional
    public void update(Long milestoneId, MilestoneRequest mileStoneRequest) {
        Milestone milestone = MilestoneRequest.toEntity(milestoneId,mileStoneRequest);
        milestoneRepository.update(milestone);
    }

    @Transactional
    public void delete(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }

    public MileStonesResponse readAll(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle)
                .orElseThrow();
        List<Milestone> milestones = milestoneRepository.readAllByOrganizationId(organizationId);
        return MileStonesResponse.from(milestones);
    }

    @Transactional
    public void updateStatus(Long milestoneId, boolean isClosed) {
        milestoneRepository.updateStatus(milestoneId, isClosed);
    }
}
