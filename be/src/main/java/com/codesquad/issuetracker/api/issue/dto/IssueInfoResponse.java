package com.codesquad.issuetracker.api.issue.dto;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueInfoResponse {

    private final List<IssueAssigneeVo> assignees;
    private final List<IssueLabelVo> labels;
    private final List<Emoticon> emoticons;

}
