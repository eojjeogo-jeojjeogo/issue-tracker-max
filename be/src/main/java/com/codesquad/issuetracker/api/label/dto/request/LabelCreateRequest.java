package com.codesquad.issuetracker.api.label.dto.request;

import com.codesquad.issuetracker.api.label.domain.Label;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelCreateRequest {

    @NotNull
    @Size(min = 1, max = 20, message = "유효하지 않은 레이블 제목 형식입니다.")
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
