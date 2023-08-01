package com.codesquad.issuetracker.api.comment.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.dto.request.CommentCreateRequest;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long create(Long issueId, CommentCreateRequest commentCreateRequest) {
        Comment comment = CommentCreateRequest.toEntity(issueId, commentCreateRequest);
        return commentRepository.create(comment).orElseThrow();
    }
}
