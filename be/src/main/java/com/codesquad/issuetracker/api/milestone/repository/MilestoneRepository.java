package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;

public interface MilestoneRepository {

    Long save(Milestone milestone);
}
