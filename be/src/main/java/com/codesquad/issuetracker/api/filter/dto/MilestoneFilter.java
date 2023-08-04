package com.codesquad.issuetracker.api.filter.dto;

public class MilestoneFilter {

    private long id;
    private String name;

    public MilestoneFilter(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
