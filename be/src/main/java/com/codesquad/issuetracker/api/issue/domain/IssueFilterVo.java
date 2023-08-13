package com.codesquad.issuetracker.api.issue.domain;

import com.codesquad.issuetracker.api.label.dto.response.LabelResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class IssueFilterVo {
    private Long id;
    private Long number;
    private String title;
    private LocalDateTime createdTime;
    private String author;
    private String authorUrl;
    private String milestoneTitle;
    private List<LabelResponse> labels;
}
