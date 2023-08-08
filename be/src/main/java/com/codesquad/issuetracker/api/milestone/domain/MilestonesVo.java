package com.codesquad.issuetracker.api.milestone.domain;

import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class MilestonesVo {

    private final Long id;
    private final String title;
    private final String description;
    private final LocalDate dueDate;
    private final Integer issueClosedCount;
    private final Integer issueOpenedCount;
    @JsonIgnore
    private final Boolean isClosed;

    public static List<MilestonesVo> classifyByFilterStatus(List<MilestonesVo> milestonesVos,
        FilterStatus filterStatus) {
        return milestonesVos.stream()
            .filter(milestonesVo -> filterStatus.isStatusMatchingFilter(milestonesVo))
            .collect(Collectors.toUnmodifiableList());
    }

}
