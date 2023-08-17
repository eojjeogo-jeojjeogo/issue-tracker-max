package com.codesquad.issuetracker.api.milestone.dto.response;

import com.codesquad.issuetracker.api.milestone.domain.MilestonesVo;
import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestonesResponse {

    private long milestoneOpenedCount;
    private long milestoneClosedCount;
    @JsonProperty("milestones")
    private List<MilestonesVo> milestonesVos;

    public static MilestonesResponse from(List<MilestonesVo> milestonesVos,
                                          FilterStatus filterStatus) {
        long closedCount = getClosedCount(milestonesVos);
        long openedCount = getOpenedCount(closedCount, milestonesVos);
        List<MilestonesVo> mileStonesResponse = MilestonesVo.classifyByFilterStatus(milestonesVos,
                filterStatus);
        return new MilestonesResponse(openedCount, closedCount, mileStonesResponse);
    }

    private static long getOpenedCount(long closedCount, List<MilestonesVo> mileStonesResponse) {
        return mileStonesResponse.size() - closedCount;
    }

    private static long getClosedCount(List<MilestonesVo> milestones) {
        return milestones.stream()
                .filter(MilestonesVo::getIsClosed)
                .count();
    }
}
