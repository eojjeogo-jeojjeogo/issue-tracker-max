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
    private final LocalDateTime createdTime;
    private final Long issueId;
    private final Long memberId;

}
