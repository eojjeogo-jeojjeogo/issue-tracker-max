package com.codesquad.issuetracker.api.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LabelFilter {

    private long id;
    private String name;
    private String backgroundColor;
    private Boolean isDark;
}
