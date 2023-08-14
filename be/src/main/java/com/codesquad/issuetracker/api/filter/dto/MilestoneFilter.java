package com.codesquad.issuetracker.api.filter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestoneFilter {

    private Long id;
    private String name;
}
