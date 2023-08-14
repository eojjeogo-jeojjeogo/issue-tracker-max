package com.codesquad.issuetracker.api.comment.dto.response;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentEmoticonResponse {

    private Long id;
    @JsonProperty("emoticon")
    private String unicode;
    private List<String> memberNickname;

    public static CommentEmoticonResponse from(CommentEmoticonVo CommentEmoticonVo) {
        return CommentEmoticonResponse.builder()
                .id(CommentEmoticonVo.getId())
                .unicode(CommentEmoticonVo.getEmoticon())
                .memberNickname(CommentEmoticonVo.getMemberNickname())
                .build();
    }

}
