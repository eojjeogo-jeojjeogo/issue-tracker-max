package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.util.List;

public interface CommentEmoticonRepository {

    void save(Long commentId, Long memberId, Emoticon emoticon);

    List<CommentEmoticonVo> findAllBy(Long commentId);
}
