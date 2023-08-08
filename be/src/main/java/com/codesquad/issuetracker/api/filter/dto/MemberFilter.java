package com.codesquad.issuetracker.api.filter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MemberFilter {

    private final Long id;
    private final String name;
    private final String imgUrl;
}
