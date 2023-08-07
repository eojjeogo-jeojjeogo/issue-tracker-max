package com.codesquad.issuetracker.api.label.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Label {

    private Long id;
    private Long organizationId;
    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

}
