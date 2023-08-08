package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import com.codesquad.issuetracker.api.comment.dto.response.CommentResponse;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueResponse {

    private Long id;
    private Long number;
    private String title;
    private Boolean isClosed;
    private LocalDateTime createdTime;
    private String author;

    private List<CommentResponse> comments;
    private MilestoneVo milestone;
    private List<IssueAssigneeVo> assignees;
    private List<IssueLabelVo> labels;
    private List<Emoticon> emoticons;

    public static IssueResponse of(IssueVo issueVo, List<CommentResponse> comments, MilestoneVo milestone,
                                   List<IssueAssigneeVo> assignees, List<IssueLabelVo> labels,
                                   List<Emoticon> emoticons) {
        return IssueResponse.builder()
                .id(issueVo.getId())
                .number(issueVo.getNumber())
                .title(issueVo.getTitle())
                .isClosed(issueVo.getIsClosed())
                .createdTime(issueVo.getCreatedTime())
                .author(issueVo.getAuthor())
                .comments(comments)
                .milestone(milestone)
                .assignees(assignees)
                .labels(labels)
                .emoticons(emoticons)
                .build();
    }
}
