package com.codesquad.issuetracker.api.comment.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class Comment {

    private final Long id;
    private final String content;
    private final LocalDateTime localDateTime;
    private final String fileUrl;
    private final Long issueId;
    //member_id 일단 1로 고정
    private final Long memberId = 1L;
}
