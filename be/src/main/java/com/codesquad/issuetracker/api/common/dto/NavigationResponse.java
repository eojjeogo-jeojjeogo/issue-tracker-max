package com.codesquad.issuetracker.api.common.dto;

public class NavigationResponse {

    private Long labelsCount;
    private Long milestonesCount;

    public NavigationResponse(Long labelsCount, Long milestonesCount) {
        this.labelsCount = labelsCount;
        this.milestonesCount = milestonesCount;
    }

    public Long getLabelsCount() {
        return labelsCount;
    }

    public Long getMilestonesCount() {
        return milestonesCount;
    }
}
