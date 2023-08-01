package com.codesquad.issuetracker.api.milestone.dto.request;

import static com.codesquad.issuetracker.common.unit.ConverterFormatter.DATE_TIME_PATTERN;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MilestoneCreateRequest {

    private String title;
    private String description;
    private String dueDate;

    public MilestoneCreateRequest() {
    }

    public MilestoneCreateRequest(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static Milestone toEntity(MilestoneCreateRequest mileStoneCreateRequest, Long organizationId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(mileStoneCreateRequest.getDueDate(), formatter);
        return Milestone.builder()
                .organizationId(organizationId)
                .title(mileStoneCreateRequest.getTitle())
                .description(mileStoneCreateRequest.getDescription())
                .isClosed(false)
                .dueDate(localDate)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
}
