package com.codesquad.issuetracker.api.comment.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Emoticon {

    private final Long id;
    private final String unicode;

}
