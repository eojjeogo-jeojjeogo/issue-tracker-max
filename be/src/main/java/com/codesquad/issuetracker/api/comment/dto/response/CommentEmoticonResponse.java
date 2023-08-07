package com.codesquad.issuetracker.api.comment.dto.response;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentEmoticonResponse {

    private Long id;
    private String emoticon;
    private List<String> memberNickname;

    public static CommentEmoticonResponse from(CommentEmoticonVo CommentEmoticonVo) {
        return CommentEmoticonResponse.builder()
            .id(CommentEmoticonVo.getId())
            .emoticon(CommentEmoticonVo.getEmoticon())
            .memberNickname(CommentEmoticonVo.getMemberNickname())
            .build();
    }

}
