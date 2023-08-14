package com.codesquad.issuetracker.api.issue.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class IssueFilterRequest {
    private Boolean isClosed;
    private Long assignee;
    private List<Long> labels;
    private Long milestone;
    private Long author;
    private Long includeMemberComment;
}
