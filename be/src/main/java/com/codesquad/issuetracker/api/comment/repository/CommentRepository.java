package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import java.util.Optional;

public interface CommentRepository {

    Optional<Long> create(Comment comment);

    Long update(Comment comment);
}
