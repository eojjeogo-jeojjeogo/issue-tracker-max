package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.IssueCommentVo;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Long> save(Comment comment);

    Long update(Comment comment);

    void deleteBy(Long issueId); // 이거 이슈 아이디로 수정??

    List<IssueCommentVo> findAllBy(Long issueId, String issueAuthor);
}
