package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.util.List;
import java.util.Optional;

public interface MilestoneRepository {

    Optional<Long> save(Milestone milestone);

    Optional<Milestone> findById(Long milestoneId);

    void update(Milestone milestone);

    void deleteById(Long milestoneId);

    List<Milestone> readAllByOrganizationId(Long organizationId);

    void updateStatus(Long milestoneId, boolean isClosed);

    Long findCountByOrganizationId(Long organizationId);

    List<MilestoneFilter> findFilterByOrganizationId(Long organizationId);
}
