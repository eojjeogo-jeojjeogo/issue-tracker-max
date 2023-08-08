package com.codesquad.issuetracker.api.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneFilter {

    private long id;
    private String name;
}
