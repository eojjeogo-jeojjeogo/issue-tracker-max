package com.codesquad.issuetracker.api.comment.controller;

import com.codesquad.issuetracker.api.comment.dto.request.CommentCreateRequest;
import com.codesquad.issuetracker.api.comment.service.CommentService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/{organizationTitle}/issues/{issueId}/comments")
    public ResponseEntity<Map<String, Long>> create(@PathVariable Long issueId,
        @RequestBody CommentCreateRequest commentCreateRequest) {
        Long commentId = commentService.create(issueId, commentCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("id", commentId));
    }
}
