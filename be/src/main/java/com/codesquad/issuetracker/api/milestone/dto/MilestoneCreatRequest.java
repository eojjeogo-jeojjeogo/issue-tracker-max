package com.codesquad.issuetracker.api.milestone.dto;

public class MilestoneCreatRequest {

    private String title;
    private String description;
    private String dueDate;

    public MilestoneCreatRequest() {
    }

    public MilestoneCreatRequest(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
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
