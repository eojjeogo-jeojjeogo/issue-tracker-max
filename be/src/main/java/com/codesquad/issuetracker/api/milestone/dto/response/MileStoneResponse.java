package com.codesquad.issuetracker.api.milestone.dto.response;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MileStoneResponse {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dueDate;
    private Long issueClosedCount;
    private Long issueOpenedCount;

    public MileStoneResponse(Long id, String title, String description, LocalDate dueDate, Long issueClosedCount,
            Long issueOpenedCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.issueClosedCount = issueClosedCount;
        this.issueOpenedCount = issueOpenedCount;
    }

    public static MileStoneResponse from(Milestone milestone) {
        return new MileStoneResponse(milestone.getId(), milestone.getTitle(), milestone.getDescription(),
                milestone.getDueDate(), 0L, 0L);
    }

    public static List<MileStoneResponse> from(List<Milestone> milestones) {
        return milestones.stream()
                .map(MileStoneResponse::from)
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

    public Long getIssueClosedCount() {
        return issueClosedCount;
    }

    public Long getIssueOpenedCount() {
        return issueOpenedCount;
    }
}
