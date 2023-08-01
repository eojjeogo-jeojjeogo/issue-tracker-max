package com.codesquad.issuetracker.api.label.dto;

import com.codesquad.issuetracker.api.label.domain.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelCreateRequest {

    private Long organizationId;
    private String title;
    private String description;
    private String backgroundColor;
    private Boolean isDark;

    public LabelCreateRequest(String title, String description, String backgroundColor, boolean isDark) {
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.isDark = isDark;
    }

    public static Label toEntity(Long organizationId, LabelCreateRequest labelRequest) {
        return Label.builder()
                .organizationId(organizationId)
                .title(labelRequest.title)
                .description(labelRequest.description)
                .backgroundColor(labelRequest.backgroundColor)
                .isDark(labelRequest.isDark)
                .build();
    }
}
