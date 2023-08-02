package com.codesquad.issuetracker.api.issue.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.dto.request.CommentCreateRequest;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.repository.IssueRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final OrganizationRepository organizationRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long create(String organizationTitle, IssueCreateRequest issueCreateRequest) {
        // TODO: 회원 확인 로직 추가 필요, orElseThrow() 예외 처리 필요
        // 이슈 저장
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        Long issuesCount = issueRepository.countIssuesBy(organizationId).orElseThrow();
        Issue issue = IssueCreateRequest.toEntity(organizationId, issuesCount + 1, issueCreateRequest);
        Long issueId = issueRepository.save(issue).orElseThrow();

        // 코멘트, 담당자, 라벨 저장
        saveComment(issueId, issueCreateRequest);
        saveAssignees(issueId, issueCreateRequest);
        saveLabels(issueId, issueCreateRequest);

        return issueId;
    }

    private void saveComment(Long issueId, IssueCreateRequest issueCreateRequest) {
        CommentCreateRequest commentCreateRequest = issueCreateRequest.getComment();
        Comment comment = CommentCreateRequest.toEntity(issueId, commentCreateRequest);
        commentRepository.create(comment);
    }

    private void saveAssignees(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueAssignee> issueAssignees = IssueCreateRequest.extractAssignees(issueId, issueCreateRequest);
        issueRepository.save(issueAssignees);
    }

    private void saveLabels(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueLabel> issueLabels = IssueCreateRequest.extractLabels(issueId, issueCreateRequest);
        issueRepository.save(issueLabels);
    }

}
