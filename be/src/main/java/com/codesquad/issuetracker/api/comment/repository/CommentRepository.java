package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.IssueCommentVo;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Long> create(Comment comment);

    Long update(Comment comment);

    void delete(Long commentId);

    List<IssueCommentVo> findAllByIssueId(Long issueId, String issueAuthor);
}
