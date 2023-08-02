package com.codesquad.issuetracker.api.milestone.dto.response;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MileStonesResponse {

    private long milestoneOpenedCount;
    private long milestoneClosedCount;
    @JsonProperty("milestones")
    private List<MileStoneResponse> mileStoneResponseCollection;

    public MileStonesResponse(long milestoneOpenedCount, long milestoneClosedCount,
            List<MileStoneResponse> mileStoneResponseCollection) {
        this.milestoneOpenedCount = milestoneOpenedCount;
        this.milestoneClosedCount = milestoneClosedCount;
        this.mileStoneResponseCollection = mileStoneResponseCollection;
    }

    public static MileStonesResponse from(List<Milestone> milestones) {
        long closedCount = getClosedCount(milestones);
        long openedCount = getOpenedCount(closedCount, milestones);
        List<MileStoneResponse> mileStonesResponse = MileStoneResponse.from(milestones);
        return new MileStonesResponse(openedCount, closedCount, mileStonesResponse);
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

    public List<MileStoneResponse> getMileStoneResponseCollection() {
        return mileStoneResponseCollection;
    }
}
