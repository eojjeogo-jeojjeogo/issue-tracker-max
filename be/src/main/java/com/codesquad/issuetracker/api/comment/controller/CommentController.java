package com.codesquad.issuetracker.api.comment.controller;

import static com.codesquad.issuetracker.common.util.RequestUtil.extractMemberId;

import com.codesquad.issuetracker.api.comment.dto.request.CommentEmoticonAddRequest;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.service.CommentService;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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

    public static final String MEMBER_ID = "memberId";
    private final CommentService commentService;

    @PostMapping("/api/{organizationTitle}/issues/{issueId}/comments")
    public ResponseEntity<Map<String, Long>> createComment(HttpServletRequest httpServletRequest,
                                                           @PathVariable Long issueId,
                                                           @RequestBody CommentRequest commentRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        Long commentId = commentService.createComment(issueId, commentRequest, memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", commentId));
    }

    @PatchMapping("/api/{organizationTitle}/issues/{issueId}/comments/{commentId}")
    public ResponseEntity<Map<String, Long>> updateComment(@PathVariable Long commentId,
                                                           @RequestBody CommentRequest commentRequest) {
        Long updatedCommentId = commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("id", updatedCommentId));
    }

    @PostMapping("/api/{organizationTitle}/issues/{issueId}/comments/{commentId}/emoticons")
    public ResponseEntity<Void> createCommentEmoticon(HttpServletRequest httpServletRequest,
                                                      @PathVariable Long commentId,
                                                      @RequestBody CommentEmoticonAddRequest commentEmoticonAddRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        commentService.createCommentEmoticon(commentId, memberId, commentEmoticonAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
