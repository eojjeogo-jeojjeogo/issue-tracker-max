package com.codesquad.issuetracker.api.comment.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import com.codesquad.issuetracker.api.comment.domain.IssueCommentVo;
import com.codesquad.issuetracker.api.comment.dto.request.CommentEmoticonAddRequest;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.dto.response.CommentEmoticonResponse;
import com.codesquad.issuetracker.api.comment.dto.response.CommentResponse;
import com.codesquad.issuetracker.api.comment.repository.CommentEmoticonRepository;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentEmoticonRepository commentEmoticonRepository;

    public Long createComment(Long issueId, CommentRequest commentRequest, Long memberId) {
        Comment comment = commentRequest.toEntityWithIssueId(issueId, memberId);
        return commentRepository.save(comment).orElseThrow();
    }

    @Transactional
    public List<CommentResponse> readAll(Long issueId, String issueAuthor) {
        List<IssueCommentVo> issueCommentVos = commentRepository.findAllBy(issueId,
                issueAuthor);

        //comment 안에 존재하는 emoticon 정보를 가져온다.
        Map<Long, List<CommentEmoticonResponse>> commentEmoticonResponses = issueCommentVos.stream()
                .collect(Collectors.toMap(
                        issueCommentVo -> issueCommentVo.getId(),
                        issueCommentVo -> commentEmoticonRepository.findAllBy(
                                        issueCommentVo.getId())
                                .stream()
                                .map(CommentEmoticonResponse::from)
                                .collect(Collectors.toList())
                ));

        return CommentResponse.of(issueCommentVos, commentEmoticonResponses);
    }

    public Long updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntityWithCommentId(commentId);
        return commentRepository.update(comment);
    }

    public void createCommentEmoticon(Long commentId, Long memberId,
                                      CommentEmoticonAddRequest commentEmoticonAddRequest) {
        Emoticon emoticon = commentEmoticonAddRequest.toEntity();
        commentEmoticonRepository.save(commentId, memberId, emoticon);
    }
}
