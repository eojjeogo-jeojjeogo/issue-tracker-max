package com.codesquad.issuetracker.api.milestone.dto.response;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MilestonesResponse {

    private long milestoneOpenedCount;
    private long milestoneClosedCount;
    @JsonProperty("milestones")
    private List<MilestoneResponse> milestoneResponseCollection;

    public MilestonesResponse(long milestoneOpenedCount, long milestoneClosedCount,
        List<MilestoneResponse> milestoneResponseCollection) {
        this.milestoneOpenedCount = milestoneOpenedCount;
        this.milestoneClosedCount = milestoneClosedCount;
        this.milestoneResponseCollection = milestoneResponseCollection;
    }

    public static MilestonesResponse from(List<Milestone> milestones, FilterStatus filterStatus) {
        long closedCount = getClosedCount(milestones);
        long openedCount = getOpenedCount(closedCount, milestones);
        List<MilestoneResponse> mileStonesResponse = MilestoneResponse.from(milestones,
            filterStatus);
        return new MilestonesResponse(openedCount, closedCount, mileStonesResponse);
    }

    private static long getOpenedCount(long closedCount, List<Milestone> mileStonesResponse) {
        return mileStonesResponse.size() - closedCount;
    }

    private static long getClosedCount(List<Milestone> milestones) {
        return milestones.stream()
            .filter(Milestone::isClosed)
            .count();
    }

    public long getMilestoneOpenedCount() {
        return milestoneOpenedCount;
    }

    public long getMilestoneClosedCount() {
        return milestoneClosedCount;
    }
}
