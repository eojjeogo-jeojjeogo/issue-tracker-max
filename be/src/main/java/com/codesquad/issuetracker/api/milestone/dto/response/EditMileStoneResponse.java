package com.codesquad.issuetracker.api.milestone.dto.response;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EditMileStoneResponse {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dueDate;

    public EditMileStoneResponse(Long id, String title, String description, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static EditMileStoneResponse from(Milestone milestone) {
        return new EditMileStoneResponse(milestone.getId(), milestone.getTitle(), milestone.getDescription(),
                milestone.getDueDate());
    }

    public static List<EditMileStoneResponse> from(List<Milestone> milestones) {
        return milestones.stream()
                .map(EditMileStoneResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
