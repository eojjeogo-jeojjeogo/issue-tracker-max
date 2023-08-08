package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class IssuesStatusUpdateRequest {

    private List<Long> issues;
    private Boolean isClosed;

    public List<Issue> toEntity() {
        return issues.stream().map(issue -> Issue.builder()
                        .id(issue)
                        .isClosed(isClosed)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }
}
