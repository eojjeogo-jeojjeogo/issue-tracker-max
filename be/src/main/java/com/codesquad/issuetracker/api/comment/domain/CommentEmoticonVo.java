package com.codesquad.issuetracker.api.comment.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
/**
 * issue 상세페이지 조회시 각각의 Commnet에 달려있는 emoticon 정보를 나타내는 VO
 * 즉 하나의 Comment에 CommentEmoticonVo가 List의 형태로 들어갈수 있습니다. (하나의 comment에 여러개의 이모티콘이 붙을수 있기 때문에)
 */
public class CommentEmoticonVo {

    private final Long id;
    private final String emoticon;
    private final List<String> memberNickname;

}
