package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.util.Optional;

public interface MilestoneRepository {

    Optional<Long> save(Milestone milestone);

    Optional<Milestone> findById(Long milestoneId);
}
