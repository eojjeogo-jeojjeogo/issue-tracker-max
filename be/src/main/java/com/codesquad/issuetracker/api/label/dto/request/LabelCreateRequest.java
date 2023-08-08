package com.codesquad.issuetracker.api.label.dto.request;

import com.codesquad.issuetracker.api.label.domain.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelCreateRequest {

    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

    public LabelCreateRequest() {
    }

    public Label toEntity(Long organizationId) {
        return Label.builder()
                .organizationId(organizationId)
                .title(this.title)
                .description(this.description)
                .backgroundColor(this.backgroundColor)
                .isDark(this.isDark)
                .build();
    }
}
