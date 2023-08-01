package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class CommentCreateRequest {

    private String content;
    private String fileUrl;

    @Builder
    public CommentCreateRequest(String content, String fileUrl) {
        this.content = content;
        this.fileUrl = fileUrl;
    }

    public static Comment toEntity(Long issueId, CommentCreateRequest commentCreateRequest) {
        return Comment.builder()
            .issueId(issueId)
            .content(commentCreateRequest.content)
            .fileUrl(commentCreateRequest.fileUrl)
            .build();
    }
}
