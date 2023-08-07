package com.codesquad.issuetracker.api.comment.dto.response;

import com.codesquad.issuetracker.api.comment.domain.IssueCommentVo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private String author;
    private String authorImg;
    private Boolean isIssueAuthor;
    private LocalDateTime createdTime;
    private List<CommentEmoticonResponse> emoticons;
    private String files; // TODO: 파일 첨부는 일단 1개만 가능하고, 이후 List로 변경 예정

    private static CommentResponse of(IssueCommentVo issueCommentVo,
        Map<Long, List<CommentEmoticonResponse>> commentEmoticonResponses) {

        return CommentResponse.builder()
            .id(issueCommentVo.getId())
            .content(issueCommentVo.getContent())
            .files(issueCommentVo.getFiles())
            .author(issueCommentVo.getAuthor())
            .authorImg(issueCommentVo.getAuthorImg())
            .isIssueAuthor(issueCommentVo.isIssueAuthor())
            .createdTime(issueCommentVo.getCreatedTime())
            .emoticons(commentEmoticonResponses.get(issueCommentVo.getId()))
            .build();
    }

    /**
     * commentEmoticonResponses 가 Map 인 이유는 어떤 commentId에 CommentEmoticonResponse 가 할당되야하는지 확인하기
     * 위해서이다.
     *
     * @param issueCommentVos
     * @param commentEmoticonResponses
     * @return
     */
    public static List<CommentResponse> of(List<IssueCommentVo> issueCommentVos,
        Map<Long, List<CommentEmoticonResponse>> commentEmoticonResponses) {
        return issueCommentVos.stream()
            .map(IssueCommentVo -> CommentResponse.of(IssueCommentVo, commentEmoticonResponses))
            .collect(Collectors.toUnmodifiableList());
    }

}
