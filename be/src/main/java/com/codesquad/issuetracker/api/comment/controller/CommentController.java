package com.codesquad.issuetracker.api.comment.controller;

import com.codesquad.issuetracker.api.comment.dto.request.CommentEmoticonAddRequest;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.service.CommentService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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
        @RequestBody CommentRequest commentRequest) {
        Long commentId = commentService.create(issueId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("id", commentId));
    }

    @PatchMapping("/api/{organizationTitle}/issues/{issueId}/comments/{commentId}")
    public ResponseEntity<Map<String, Long>> update(@PathVariable Long commentId,
        @RequestBody CommentRequest commentRequest) {
        Long updatedCommentId = commentService.update(commentId, commentRequest);
        return ResponseEntity.ok()
            .body(Map.of("id", updatedCommentId));
    }

    @PostMapping("/api/{organizationTitle}/issues/{issueId}/comments/{commentId}/emoticons")
    public ResponseEntity<Void> addEmoticon(@PathVariable Long commentId,
        @RequestBody CommentEmoticonAddRequest commentEmoticonAddRequest) {
        //todo 로그인 된 유저 정보가 필요함
        Long memberId = 1L;
        commentService.addEmoticon(commentId, memberId, commentEmoticonAddRequest);
        return ResponseEntity.ok().build();
    }
}
