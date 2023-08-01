package com.codesquad.issuetracker.api.milestone.dto.request;

import static com.codesquad.issuetracker.common.unit.ConverterFormatter.DATE_TIME_PATTERN;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MilestoneRequest {

    private String title;
    private String description;
    private String dueDate;

    public MilestoneRequest() {
    }

    public MilestoneRequest(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static Milestone toEntity(MilestoneRequest mileStoneRequest, Long organizationId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(mileStoneRequest.getDueDate(), formatter);
        return Milestone.builder()
                .organizationId(organizationId)
                .title(mileStoneRequest.getTitle())
                .description(mileStoneRequest.getDescription())
                .isClosed(false)
                .dueDate(localDate)
                .build();
    }

    public static Milestone toEntity(Long milestoneId, MilestoneRequest mileStoneRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(mileStoneRequest.getDueDate(), formatter);
        return Milestone.builder()
                .id(milestoneId)
                .title(mileStoneRequest.getTitle())
                .description(mileStoneRequest.getDescription())
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
