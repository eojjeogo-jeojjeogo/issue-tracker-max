package com.codesquad.issuetracker.api.milestone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class MilestoneVo {

    private final Long id;
    private final String title;
    private final Integer issueClosedCount;
    private final Integer issueOpenedCount;

}
