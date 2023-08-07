package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.util.List;

public interface CommentEmoticonRepository {

    List<CommentEmoticonVo> findAllEmoticonsByCommentId(Long commentId);

    void addEmoticon(Long commentId, Long memberId, Emoticon emoticon);
}
