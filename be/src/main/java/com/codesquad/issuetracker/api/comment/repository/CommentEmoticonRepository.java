package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import java.util.List;

public interface CommentEmoticonRepository {

    List<CommentEmoticonVo> findAllEmoticonsByCommentId(Long commentId);
}
