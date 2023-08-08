package com.codesquad.issuetracker.api.milestone.dto.request;

import lombok.Getter;

@Getter
public class MilestoneStatusRequest {

    private boolean isClosed;

    public boolean isClosed() {
        return isClosed;
    }
}
