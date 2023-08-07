package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCreateRequest {

    private Long organizationId;
    private Long milestonesId;
    private Long memberId = 1L; // TODO: 임의로 1을 넣어놓은 상황

    private String title;
    private CommentRequest comment;
    private List<Long> assignees;
    private List<Long> labels;
    private final Boolean isClosed = false;

    public Issue toEntity(Long organizationId, Long issuesCount) {
        return Issue.builder()
                .organizationId(organizationId)
                .number(issuesCount)
                .milestoneId(milestonesId)
                .memberId(memberId)
                .title(title)
                .isClosed(isClosed)
                .build();
    }

    public List<IssueAssignee> extractAssignees(Long issueId) {
        return assignees.stream()
                .map(assignee -> new IssueAssignee(issueId, assignee))
                .collect(Collectors.toList());
    }

    public List<IssueLabel> extractLabels(Long issueId) {
        return labels.stream()
                .map(label -> new IssueLabel(issueId, label))
                .collect(Collectors.toList());
    }
}
