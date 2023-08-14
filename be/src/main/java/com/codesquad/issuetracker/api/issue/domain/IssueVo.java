package com.codesquad.issuetracker.api.issue.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueVo {

    private Long id;
    private Long milestoneId;
    private Long number;
    private String title;
    private Boolean isClosed;
    private LocalDateTime createdTime;
    private String author;

}
