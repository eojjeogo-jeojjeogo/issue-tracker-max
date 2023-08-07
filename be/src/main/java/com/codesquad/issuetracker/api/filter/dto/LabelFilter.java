package com.codesquad.issuetracker.api.filter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class LabelFilter {

    private long id;
    private String name;
    private String backgroundColor;
    @JsonProperty(value = "isDark")
    private boolean isDark;

    @Builder
    private LabelFilter(long id, String name, String backgroundColor, boolean isDark) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.isDark = isDark;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
    @JsonIgnore
    public boolean isDark() {
        return isDark;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
