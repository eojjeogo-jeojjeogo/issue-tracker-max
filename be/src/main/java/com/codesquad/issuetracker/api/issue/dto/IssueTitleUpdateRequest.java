package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import lombok.Getter;

@Getter
public class IssueTitleUpdateRequest {

    private String title;

    public Issue toEntity(Long issueId) {
        return new Issue(issueId, title);
    }
}
