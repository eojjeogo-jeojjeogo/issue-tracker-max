package com.codesquad.issuetracker.api.issue.service;

import com.codesquad.issuetracker.api.comment.dto.response.CommentResponse;
import com.codesquad.issuetracker.api.comment.service.CommentService;
import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueFilterVo;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueFilterRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueFilterResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueInfoResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueMilestoneUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueTitleUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssuesStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.repository.IssueFilterMapper;
import com.codesquad.issuetracker.api.issue.repository.IssueRepository;
import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.IssueException;
import com.codesquad.issuetracker.common.exception.customexception.OrganizationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueService {

    // TODO: 도메인 설계 개선 필요(상위 개념을 만들어서 중구난방으로 서비스와 레포지토리를 가져오지 않게)
    // TODO: 코멘트 관련 내용도 어떤 것은 IssueInfo 어떤 것은 Issue에서 처리하고 있음
    private final IssueInfoService issueInfoService;
    private final CommentService commentService;
    private final MilestoneService milestoneService;

    private final OrganizationRepository organizationRepository;
    private final IssueRepository issueRepository;
    private final IssueFilterMapper issueFilterMapper;

    @Transactional
    public Long create(String organizationTitle, IssueCreateRequest issueCreateRequest) {
        // 이슈 저장
        Long organizationId = getOrganizationId(organizationTitle);
        Long issuesCount = issueRepository.countIssuesBy(organizationId).orElseThrow();
        Issue issue = issueCreateRequest.toEntity(organizationId, issuesCount + 1);
        Long issueId = issueRepository.save(issue)
                .orElseThrow(() -> new CustomRuntimeException(IssueException.ISSUE_SAVE_FAIL_EXCEPTION));

        // 코멘트, 담당자, 라벨 저장
        issueInfoService.saveIssueInfo(issueId, issueCreateRequest);
        return issueId;
    }

    @Transactional
    public IssueResponse read(Long issueId) {
        // 이슈
        IssueVo issueVo = issueRepository.findBy(issueId);
        // 마일스톤
        MilestoneVo milestone = milestoneService.read(
                issueVo.getMilestoneId()); // TODO 이거는 마일스톤 서비스보다 이슈 서비스에서 하는 것이 나을 듯
        // 코멘트
        List<CommentResponse> comments = commentService.readAll(issueId, issueVo.getAuthor());
        // 담당자, 라벨, 이모티콘 목록
        IssueInfoResponse issueInfoResponse = issueInfoService.readIssueInfo(issueId);
        return IssueResponse.of(issueVo, comments, milestone, issueInfoResponse);
    }

    public void update(Long issueId, IssueTitleUpdateRequest issueTitleUpdateRequest) {
        Issue issue = issueTitleUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateTitle(issue)) {
            throw new CustomRuntimeException(IssueException.ISSUE_TITLE_UPDATE_FAIL_EXCEPTION);
        }
    }

    public void update(Long issueId, IssueMilestoneUpdateRequest issueMilestoneUpdateRequest) {
        Issue issue = issueMilestoneUpdateRequest.toEntity(issueId);
        if (!issueRepository.updateMilestone(issue)) {
            throw new CustomRuntimeException(IssueException.ISSUE_MILESTONE_UPDATE_FAIL_EXCEPTION);
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
        issueInfoService.delete(issueId);
    }

    @Transactional
    public IssueFilterResponse readFilteredIssue(IssueFilterRequest issueFilterRequest, String organizationTitle) {
        Long organizationId = getOrganizationId(organizationTitle);
        Long openedIssuesCount = issueRepository.countOpenedIssuesBy(organizationId).get();
        Long closedIssueCount = issueRepository.countClosedIssuesBy(organizationId).get();
        issueFilterRequest.checkLabelsContainsZero();
        List<IssueFilterVo> issueFilterVos = issueFilterMapper.readAll(issueFilterRequest, organizationId);

        return IssueFilterResponse.of(openedIssuesCount, closedIssueCount, issueFilterVos);
    }

    private Long getOrganizationId(String organizationTitle) {
        return organizationRepository.findBy(organizationTitle)
                .orElseThrow(() -> new CustomRuntimeException(
                        OrganizationException.ORGANIZATION_NOT_FOUND_EXCEPTION));
    }

}
