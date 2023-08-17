package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class IssueCreateRequest {

    private Long organizationId;
    private Long milestonesId;
    @Setter
    private Long memberId;

    @NotNull
    @Size(min = 1, max = 30, message = "유효하지 않은 이슈 제목 형식입니다.")
    private String title;
    @Valid
    private CommentRequest comment;
    private List<Long> assigneesId;
    private List<Long> labelsId;
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
        return assigneesId.stream()
                .map(assigneeId -> new IssueAssignee(issueId, assigneeId))
                .collect(Collectors.toList());
    }

    public List<IssueLabel> extractLabels(Long issueId) {
        return labelsId.stream()
                .map(labelId -> new IssueLabel(issueId, labelId))
                .collect(Collectors.toList());
    }
}
