package com.codesquad.issuetracker.api.filter.dto;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class StaticFiltersResponse {
    private static final String OPEN_ISSUES = "열린 이슈";
    private static final String MY_WRITTEN_ISSUES = "내가 작성한 이슈";
    private static final String ASSIGNED_TO_ME_ISSUES = "나에게 할당된 이슈";
    private static final String ISSUES_I_COMMENTED_ON = "내가 댓글을 남긴 이슈";
    private static final String CLOSED_ISSUES = "닫힌 이슈";

    private List<String> staticFilters;
    private Long memberId;

    public StaticFiltersResponse(Long memberId) {
        this.memberId = memberId;
        this.staticFilters = Arrays.asList(
                OPEN_ISSUES,
                MY_WRITTEN_ISSUES,
                ASSIGNED_TO_ME_ISSUES,
                ISSUES_I_COMMENTED_ON,
                CLOSED_ISSUES
        );
    }
}
