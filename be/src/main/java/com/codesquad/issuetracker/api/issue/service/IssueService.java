package com.codesquad.issuetracker.api.issue.service;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.api.comment.dto.response.CommentResponse;
import com.codesquad.issuetracker.api.comment.repository.CommentEmoticonRepository;
import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import com.codesquad.issuetracker.api.comment.repository.EmoticonRepository;
import com.codesquad.issuetracker.api.comment.service.CommentService;
import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import com.codesquad.issuetracker.api.issue.dto.IssueAssigneeUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueLabelUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueMilestoneUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueTitleUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssuesStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.repository.IssueAssigneeRepository;
import com.codesquad.issuetracker.api.issue.repository.IssueLabelRepository;
import com.codesquad.issuetracker.api.issue.repository.IssueRepository;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
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
    private final CommentEmoticonRepository commentEmoticonRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;
    private final EmoticonRepository emoticonRepository;

    private final CommentService commentService;
    private final MilestoneService milestoneService;

    @Transactional
    public Long create(String organizationTitle, IssueCreateRequest issueCreateRequest) {
        // TODO: 회원 확인 로직 추가 필요, orElseThrow() 예외 처리 필요
        // 이슈 저장
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        Long issuesCount = issueRepository.countIssuesBy(organizationId).orElseThrow();
        Issue issue = issueCreateRequest.toEntity(organizationId, issuesCount + 1);
        Long issueId = issueRepository.save(issue).orElseThrow();

        // 코멘트, 담당자, 라벨 저장
        saveComment(issueId, issueCreateRequest);
        saveAssignees(issueId, issueCreateRequest);
        saveLabels(issueId, issueCreateRequest);

        return issueId;
    }

    private void saveComment(Long issueId, IssueCreateRequest issueCreateRequest) {
        CommentRequest commentRequest = new CommentRequest(
                issueCreateRequest.getComment().getContent(),
                issueCreateRequest.getComment().getFileUrl()
        );
        Comment comment = commentRequest.toEntityWithIssueId(issueId);
        commentRepository.create(comment);
    }

    private void saveAssignees(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueAssignee> issueAssignees = issueCreateRequest.extractAssignees(issueId);
        issueRepository.save(issueAssignees);
    }

    private void saveLabels(Long issueId, IssueCreateRequest issueCreateRequest) {
        List<IssueLabel> issueLabels = issueCreateRequest.extractLabels(issueId);
        issueRepository.save(issueLabels);
    }


    public IssueResponse read(Long issueId) {
        // 이슈
        IssueVo issueVo = issueRepository.findBy(issueId);
        // 코멘트
        List<CommentResponse> comments = commentService.readAll(issueId, issueVo.getAuthor());
        // 마일스톤
        MilestoneVo milestone = milestoneService.read(issueVo.getMilestone_id());
        // 담당자
        List<IssueAssigneeVo> assignees = issueAssigneeRepository.findAllBy(issueId);
        // 라벨
        List<IssueLabelVo> labels = issueLabelRepository.findAllBy(issueId);
        // 이모티콘
        List<Emoticon> emoticons = emoticonRepository.findAll();
        return IssueResponse.of(issueVo, comments, milestone, assignees, labels, emoticons);
    }

    public void update(Long issueId, IssueTitleUpdateRequest issueTitleUpdateRequest) {
        // TODO: 이슈 id가 해당 오가니제이션에 있는지 검증 필요
        Issue issue = issueTitleUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateTitle(issue)) {
            throw new RuntimeException("Title update failed for issueId: " + issueId);
        }
    }

    public void update(Long issueId, IssueAssigneeUpdateRequest issueAssigneeUpdateRequest) {
        // TODO: 이슈 id가 해당 오가니제이션에 있는지 검증 필요
        List<IssueAssignee> assignees = issueAssigneeUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateAssignees(assignees)) {
            throw new RuntimeException("Assignee update failed for issueId: " + issueId);
        }
    }

    public void update(Long issueId, IssueLabelUpdateRequest issueLabelUpdateRequest) {
        List<IssueLabel> labels = issueLabelUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateLabels(labels)) {
            throw new RuntimeException("Label update failed for issueId: " + issueId);
        }
    }

    public void update(Long issueId, IssueMilestoneUpdateRequest issueMilestoneUpdateRequest) {
        Issue issue = issueMilestoneUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateMilestone(issue)) {
            throw new RuntimeException("Label update failed for issueId: " + issueId);
        }
    }

    public void update(Long issueId, IssueStatusUpdateRequest issueStatusUpdateRequest) {
        Issue issue = issueStatusUpdateRequest.toEntity(issueId);
        issueRepository.updateStatus(issue);
    }

    public void update(IssuesStatusUpdateRequest issuesStatusUpdateRequest) {
        List<Issue> issues = issuesStatusUpdateRequest.toEntity();
        issueRepository.updateStatuses(issues);
    }

    @Transactional
    public void deleteOne(Long issueId) {
        issueRepository.delete(issueId);
        commentRepository.delete(issueId);
    }
}
