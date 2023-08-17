package com.codesquad.issuetracker.api.issue.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueFilterRequest {
    private Boolean isClosed;
    private Long assignee;
    private List<Long> labels;
    private Long milestone;
    private Long author;
    private Long includeMemberComment;
    private boolean labelsContainZero;

    /**
     * 이슈 필터링 요청에 labels id가 0이라면 레이블이 없는 이슈 필터링을 의미한다. 따라서 labelContainZero = true 로 바뀌며, xml에서 이 조건을 사용해 레이블이 없는 이슈를
     * 필터링한다.
     */
    public void checkLabelsContainsZero() {
        if (labels != null) {
            this.labelsContainZero = labels.contains(0L);
        }
    }
}
