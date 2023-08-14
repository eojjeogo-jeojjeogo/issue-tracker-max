package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    Optional<Long> countIssuesBy(Long organizationId);

    Optional<Long> save(Issue issue);

    IssueVo findBy(Long issueId);

    boolean updateTitle(Issue issue);

    boolean updateMilestone(Issue issue);

    void updateStatus(Issue issue);

    void updateStatuses(List<Issue> issues);

    void delete(Long issueId);
}
