package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import java.util.List;

public interface IssueAssigneeRepository {

    List<IssueAssigneeVo> findAllBy(Long issueId);

    void save(List<IssueAssignee> issueAssignees);

    boolean update(List<IssueAssignee> assignees);

    void delete(Long issueId);
}
