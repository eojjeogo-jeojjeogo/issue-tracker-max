package com.codesquad.issuetracker.api.milestone.repository;

import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import java.util.List;
import java.util.Optional;

public interface MilestoneRepository {

    Optional<MilestoneVo> findBy(Long milestoneId);

    List<MilestonesVo> findAllBy(Long organizationId);

    List<MilestoneFilter> findFiltersBy(Long organizationId);

    Optional<Long> save(Milestone milestone);

    void update(Milestone milestone);

    void update(Long milestoneId, boolean isClosed);

    void delete(Long milestoneId);

    Long countBy(Long organizationId);
}
