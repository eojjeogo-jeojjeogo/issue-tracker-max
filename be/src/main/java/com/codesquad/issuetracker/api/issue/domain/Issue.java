package com.codesquad.issuetracker.api.issue.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

    public Issue(Long id, Long milestoneId) {
        this.id = id;
        this.milestoneId = milestoneId;
    }
}
