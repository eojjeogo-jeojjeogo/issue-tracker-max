package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.IssueFilterVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueFilterResponse {
    private Long openedIssueCount;
    private Long closedIssueCount;
    private List<IssueFilterVo> issues;

    public static IssueFilterResponse of(Long openedIssueCount, Long closedIssueCount, List<IssueFilterVo> issues) {
        return IssueFilterResponse.builder()
                .openedIssueCount(openedIssueCount)
                .closedIssueCount(closedIssueCount)
                .issues(issues)
                .build();
    }

}
