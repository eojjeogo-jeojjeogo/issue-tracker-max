package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    Optional<Long> countIssuesBy(Long organizationId);

    Optional<Long> save(Issue issue);

    void save(List<?> options);

    void delete(Long issueId);
}
