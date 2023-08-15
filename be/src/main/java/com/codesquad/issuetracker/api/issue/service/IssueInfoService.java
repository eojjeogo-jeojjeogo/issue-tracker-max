package com.codesquad.issuetracker.api.issue.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import com.codesquad.issuetracker.api.comment.repository.EmoticonRepository;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import com.codesquad.issuetracker.api.issue.dto.IssueAssigneeUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueInfoResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueLabelUpdateRequest;
import com.codesquad.issuetracker.api.issue.repository.IssueAssigneeRepository;
import com.codesquad.issuetracker.api.issue.repository.IssueLabelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueInfoService {

    private final CommentRepository commentRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;
    private final EmoticonRepository emoticonRepository;

    /**
     * 새로운 이슈 저장 시, 이슈의 코멘트, 담당자, 라벨을 저장
     */
    @Transactional
    public void saveIssueInfo(Long issueId, IssueCreateRequest issueCreateRequest) {
        saveComment(issueId, issueCreateRequest);
        saveAssignees(issueId, issueCreateRequest);
        saveLabels(issueId, issueCreateRequest);
    }

    private void saveComment(Long issueId, IssueCreateRequest issueCreateRequest) {
        CommentRequest commentRequest = new CommentRequest(issueCreateRequest.getComment().getContent());
        Comment comment = commentRequest.toEntityWithIssueId(issueId, issueCreateRequest.getMemberId());
        commentRepository.save(comment);
    }

    private void saveAssignees(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueAssignee> issueAssignees = issueCreateRequest.extractAssignees(issueId);
        issueAssigneeRepository.save(issueAssignees);
    }

    private void saveLabels(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueLabel> issueLabels = issueCreateRequest.extractLabels(issueId);
        issueLabelRepository.save(issueLabels);
    }

    /**
     * 이슈 상세 페이지 조회 시, 이슈 담당자, 라벨, 전체 이모티콘 목록 정보 제공
     */
    @Transactional
    public IssueInfoResponse readIssueInfo(Long issueId) {
        // 담당자
        List<IssueAssigneeVo> assignees = issueAssigneeRepository.findAllByIssueId(issueId);
        // 라벨
        List<IssueLabelVo> labels = issueLabelRepository.findAllBy(issueId);
        // 이모티콘
        List<Emoticon> emoticons = emoticonRepository.findAll();
        return new IssueInfoResponse(assignees, labels, emoticons);
    }

    @Transactional
    public void update(Long issueId, IssueAssigneeUpdateRequest issueAssigneeUpdateRequest) {
        // TODO: 이슈 id가 해당 오가니제이션에 있는지 검증 필요
        List<IssueAssignee> assignees = issueAssigneeUpdateRequest.toEntity(issueId);
        issueAssigneeRepository.delete(issueId);
        if (!issueAssigneeRepository.update(assignees)) {
            throw new RuntimeException("Assignee update failed for issueId: " + issueId);
        }
    }

    @Transactional
    public void update(Long issueId, IssueLabelUpdateRequest issueLabelUpdateRequest) {
        List<IssueLabel> labels = issueLabelUpdateRequest.toEntity(issueId);
        issueLabelRepository.delete(issueId);
        if (!issueLabelRepository.update(labels)) {
            throw new RuntimeException("Label update failed for issueId: " + issueId);
        }
    }

    /**
     * 이슈 삭제 시, 코멘트, 라벨, 담당자 정보 삭제
     */
    @Transactional
    public void delete(Long issueId) {
        commentRepository.deleteBy(issueId);
        issueLabelRepository.delete(issueId);
        issueAssigneeRepository.delete(issueId);

    }
}
