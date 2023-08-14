package com.codesquad.issuetracker.api.label.dto.request;

import com.codesquad.issuetracker.api.label.domain.Label;
import lombok.Getter;

@Getter
public class LabelUpdateRequest {

    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

    public LabelUpdateRequest() {
    }

    public Label toEntity(Long organizationId, Long id) {
        return Label.builder()
                .id(id)
                .organizationId(organizationId)
                .title(this.title)
                .description(this.description)
                .backgroundColor(this.backgroundColor)
                .isDark(this.isDark)
                .build();
    }
}
