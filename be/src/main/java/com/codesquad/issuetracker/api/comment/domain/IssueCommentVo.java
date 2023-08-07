package com.codesquad.issuetracker.api.comment.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 * issue 상세페이지 조회시 필요한 Commnet 내용을 담고있는 VO
 */
public class IssueCommentVo {

    private final Long id;
    private final String content;
    private final String author;
    private final String authorImg;
    private final boolean isIssueAuthor;
    private final LocalDateTime createdTime;
    private final String files;

}
