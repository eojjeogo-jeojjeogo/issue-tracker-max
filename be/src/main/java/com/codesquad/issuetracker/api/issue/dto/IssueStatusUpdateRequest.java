package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import lombok.Getter;

@Getter
public class IssueStatusUpdateRequest {

    private Boolean isClosed;

    public Issue toEntity(Long issueId) {
        return Issue.builder()
                .id(issueId)
                .isClosed(isClosed)
                .build();
    }
}
