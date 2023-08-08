package com.codesquad.issuetracker.api.filter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberFilter {

    private final Long id;
    private final String name;
    private final String imgUrl;
}
