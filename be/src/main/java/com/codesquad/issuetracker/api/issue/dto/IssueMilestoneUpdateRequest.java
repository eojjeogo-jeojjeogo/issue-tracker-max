package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import lombok.Getter;

@Getter
public class IssueMilestoneUpdateRequest {

    private Long id;

    public Issue toEntity(
            Long issueId) { // TODO: 마일스톤 id가 이슈 테이블 안에서 이렇게 짰는데 IssueMileston이 Issue Entity로 변환되는 것이 맞는지 모르겠다.
        return new Issue(issueId, id);
    }
}
