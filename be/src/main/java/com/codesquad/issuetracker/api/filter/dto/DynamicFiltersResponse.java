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

        // TODO: 현재는 임시로 처리한 방법
        assignees.add(new IssueAssigneeVo(0L, "담당자가 없는 이슈", null));
        labels.add(new LabelFilter(0L, "레이블이 없는 이슈", null, null));
        milestones.add(new MilestoneResponse(0L, "마일스톤이 없는 이슈"));
    }
}
