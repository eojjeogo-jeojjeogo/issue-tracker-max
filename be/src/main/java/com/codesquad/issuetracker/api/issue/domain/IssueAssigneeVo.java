package com.codesquad.issuetracker.api.issue.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueAssigneeVo {

    private Long id;
    private String nickName;
    private String imgUrl;

}
