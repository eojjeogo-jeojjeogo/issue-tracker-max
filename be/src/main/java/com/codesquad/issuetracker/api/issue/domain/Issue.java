package com.codesquad.issuetracker.api.issue.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Issue {

    private Long id;
    private Long organizationId;
    private Long milestoneId;
    private Long memberId;

    private String title;
    private Long number;
    private Boolean isClosed;
    private LocalDate createdTime;

}
