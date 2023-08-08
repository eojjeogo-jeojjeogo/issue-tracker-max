package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
import java.util.List;

public interface IssueAssigneeRepository {

    List<IssueAssigneeVo> findAllBy(Long issueId);
}
