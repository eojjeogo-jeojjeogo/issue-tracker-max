package com.codesquad.issuetracker.api.milestone.filterStatus;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;

public enum FilterStatus {
    OPEN("open", false),
    CLOSED("closed", true);

    private final String type;
    private final boolean value;

    FilterStatus(String type, boolean value) {
        this.type = type;
        this.value = value;
    }

    public boolean getStatus() {
        return value;
    }

    public static FilterStatus from(String typeFromRequest) {
        for (FilterStatus status : FilterStatus.values()) {
            if (status.type.equalsIgnoreCase(typeFromRequest)) {
                return status;
            }
        }
        throw new IllegalArgumentException(typeFromRequest + ": 적절하지 않은 type 형태입니다. ");
    }

    public boolean isStatusMatchingFilter(Milestone milestone) {
        return milestone.isClosed() == this.getStatus();
    }
}
