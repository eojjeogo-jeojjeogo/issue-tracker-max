package com.codesquad.issuetracker.api.comment.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
/**
 * issue 상세페이지 조회시 필요한 Commnet에 달려있는 emoticon 정보를 나타내는 VO
 */
public class CommentEmoticonVo {

    private final Long id;
    private final String emoticon;
    private final List<String> memberNickname;

}
