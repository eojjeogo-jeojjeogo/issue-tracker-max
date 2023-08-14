package com.codesquad.issuetracker.api.milestone.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Milestone {

    private final Long id;
    private final String title;
    private final String description;
    private final LocalDate dueDate;
    private final Boolean isClosed;
    private final Long organizationId;
    
}
