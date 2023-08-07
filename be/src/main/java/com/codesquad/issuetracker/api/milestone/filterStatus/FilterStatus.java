package com.codesquad.issuetracker.api.milestone.filterStatus;

public enum FilterStatus {
    OPEN("open", false),
    CLOSED("closed", true);

    private final String stringValue;
    private final boolean value;

    FilterStatus(String stringValue, boolean value) {
        this.stringValue = stringValue;
        this.value = value;
    }

    public boolean getStatus() {
        return value;
    }

    public static FilterStatus fromString(String stringValue) {
        for (FilterStatus status : FilterStatus.values()) {
            if (status.stringValue.equalsIgnoreCase(stringValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException(stringValue + ": 적절하지 않은 filter 형태입니다. ");
    }
}
