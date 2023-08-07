package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import lombok.Getter;

@Getter
public class CommentEmoticonAddRequest {

    private Long id;

    public Emoticon toEntity() {
        return new Emoticon(id);
    }
}
