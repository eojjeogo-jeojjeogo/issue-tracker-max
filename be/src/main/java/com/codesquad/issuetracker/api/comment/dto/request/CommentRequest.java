package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotNull
    @Size(min = 1, max = 1000, message = "유효하지 않은 이슈 코멘트 형식입니다.")
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
