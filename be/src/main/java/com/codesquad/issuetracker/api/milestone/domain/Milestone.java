package com.codesquad.issuetracker.api.milestone.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Milestone {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isClosed;
    private Long organizationId;

    @Builder
    public Milestone(Long id, String title, String description, LocalDate dueDate, boolean isClosed,
            Long organizationId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isClosed = isClosed;
        this.organizationId = organizationId;
    }
}
