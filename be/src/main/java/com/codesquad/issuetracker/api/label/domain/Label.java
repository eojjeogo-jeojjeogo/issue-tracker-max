package com.codesquad.issuetracker.api.label.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Label {

    private final Long id;
    private final Long organizationId;
    private final String title;
    private final String description;
    private final String backgroundColor;
    private final Boolean isDark;

}
