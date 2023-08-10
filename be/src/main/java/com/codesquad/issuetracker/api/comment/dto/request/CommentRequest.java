package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class CommentRequest {

    private String content;
    private String fileUrl;

    @Builder
    public CommentRequest(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }

    public Comment toEntityWithIssueId(Long issueId, Long memberId) {
        return Comment.builder()
                .memberId(memberId)
                .issueId(issueId)
                .content(content)
                .fileUrl(fileUrl)
                .build();
    }

    public Comment toEntityWithCommentId(Long commentId) {
        return Comment.builder()
                .id(commentId)
                .content(content)
                .fileUrl(fileUrl)
                .build();
    }
}
