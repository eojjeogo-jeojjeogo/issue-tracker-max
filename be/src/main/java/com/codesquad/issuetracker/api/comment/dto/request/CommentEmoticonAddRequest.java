package com.codesquad.issuetracker.api.comment.dto.request;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentEmoticonAddRequest {

    private Long id;

    public CommentEmoticonAddRequest(Long id) {
        this.id = id;
    }

    public Emoticon toEntity() {
        return Emoticon.builder()
                .id(this.id)
                .build();
    }
}
