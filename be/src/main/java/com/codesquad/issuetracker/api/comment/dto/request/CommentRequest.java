package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private String content;

    public Comment toEntityWithIssueId(Long issueId, Long memberId) {
        return Comment.builder()
                .memberId(memberId)
                .issueId(issueId)
                .content(content)
                .build();
    }

    public Comment toEntityWithCommentId(Long commentId) {
        return Comment.builder()
                .id(commentId)
                .content(content)
                .build();
    }
}
