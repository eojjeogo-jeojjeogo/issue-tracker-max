package com.codesquad.issuetracker.api.filter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelFilter {

    private Long id;
    private String name;
    private String backgroundColor;
    private Boolean isDark;
}
