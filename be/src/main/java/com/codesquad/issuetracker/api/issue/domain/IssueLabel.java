package com.codesquad.issuetracker.api.issue.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueLabel {

    private Long issueId;
    private Long labelId;

}
