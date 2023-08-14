package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class IssueLabelUpdateRequest {

    private List<Long> labels;

    public List<IssueLabel> toEntity(Long issueId) {
        return labels.stream()
                .map(label -> new IssueLabel(issueId, label))
                .collect(Collectors.toList());
    }
}
