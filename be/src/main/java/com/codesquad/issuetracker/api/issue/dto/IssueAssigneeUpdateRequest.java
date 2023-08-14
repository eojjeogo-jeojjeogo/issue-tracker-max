package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class IssueAssigneeUpdateRequest {

    private List<Long> assignees;

    public List<IssueAssignee> toEntity(Long issueId) {
        return assignees.stream()
                .map(assignee -> new IssueAssignee(issueId, assignee))
                .collect(Collectors.toList());
    }
}
