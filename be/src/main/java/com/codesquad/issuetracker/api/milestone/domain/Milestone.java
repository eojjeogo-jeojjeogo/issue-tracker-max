package com.codesquad.issuetracker.api.milestone.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Milestone {

    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isClosed;
    private Long organizationId;

    @Builder
    private Milestone(String title, String description, LocalDate dueDate, boolean isClosed, long organizationId) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isClosed = isClosed;
        this.organizationId = organizationId;
    }
}
