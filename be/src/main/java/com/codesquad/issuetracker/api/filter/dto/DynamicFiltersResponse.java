package com.codesquad.issuetracker.api.filter.dto;

import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestoneResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DynamicFiltersResponse {

    private final List<IssueAssigneeVo> assignees;
    private final List<LabelFilter> labels;
    private final List<MilestoneResponse> milestones;
    private final List<MemberFilter> authors;

    @Builder
    public DynamicFiltersResponse(List<IssueAssigneeVo> assignees, List<LabelFilter> labels,
                                  List<MilestoneResponse> milestones, List<MemberFilter> authors) {
        this.assignees = assignees;
        this.labels = labels;
        this.milestones = milestones;
        this.authors = authors;
    }
}
