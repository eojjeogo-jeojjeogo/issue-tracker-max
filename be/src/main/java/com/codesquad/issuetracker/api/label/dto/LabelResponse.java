package com.codesquad.issuetracker.api.label.dto;

import com.codesquad.issuetracker.api.label.domain.Label;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LabelResponse {

    private Long id;
    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

    public static LabelResponse from(Label label) {
        return LabelResponse.builder()
            .id(label.getId())
            .title(label.getTitle())
            .description(label.getDescription())
            .backgroundColor(label.getBackgroundColor())
            .isDark(label.getIsDark())
            .build();
    }
}
