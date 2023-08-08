package com.codesquad.issuetracker.api.issue.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueLabelVo {

    private Long id;
    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

}
