package com.codesquad.issuetracker.api.filter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberFilter {

    private Long id;
    private String name;
    private String imgUrl;
}
