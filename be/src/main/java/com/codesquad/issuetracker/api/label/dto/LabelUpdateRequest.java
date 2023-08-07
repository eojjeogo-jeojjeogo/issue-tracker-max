package com.codesquad.issuetracker.api.label.dto;

import com.codesquad.issuetracker.api.label.domain.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelUpdateRequest {

    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

    public LabelUpdateRequest(String title, String description, String backgroundColor,
        boolean isDark) {
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.isDark = isDark;
    }

    public static Label toEntity(LabelUpdateRequest labelUpdateRequest, Long organizationId,
        Long id) {
        return Label.builder()
            .id(id)
            .organizationId(organizationId)
            .title(labelUpdateRequest.title)
            .description(labelUpdateRequest.description)
            .backgroundColor(labelUpdateRequest.backgroundColor)
            .isDark(labelUpdateRequest.isDark)
            .build();
    }
}
