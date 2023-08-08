package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    Optional<Long> countIssuesBy(Long organizationId);

    Optional<Long> save(Issue issue);

    void save(List<?> options);

    IssueVo findBy(Long issueId);

    boolean updateTitle(Issue issue);

    boolean updateAssignees(List<IssueAssignee> assignees);

    boolean updateLabels(List<IssueLabel> labels);

    boolean updateMilestone(Issue issue);

    void updateStatus(Issue issue);

    void updateStatuses(List<Issue> issues);

    void delete(Long issueId);
}
