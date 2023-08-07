package com.codesquad.issuetracker.api.comment.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long create(Long issueId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntityWithIssueId(issueId);
        return commentRepository.create(comment).orElseThrow();
    }

    public Long update(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntityWithCommentId(commentId);
        return commentRepository.update(comment);
    }
}
