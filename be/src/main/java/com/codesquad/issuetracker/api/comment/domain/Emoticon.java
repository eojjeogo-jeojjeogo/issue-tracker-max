package com.codesquad.issuetracker.api.comment.domain;

import lombok.Getter;

@Getter
public class Emoticon {

    private Long id;
    private String unicode;

    public Emoticon(Long id) {
        this.id = id;
    }

}
