package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import java.util.List;

public interface IssueLabelRepository {

    List<IssueLabelVo> findAllBy(Long issueId);

    void save(List<IssueLabel> issueLabels);

    boolean update(List<IssueLabel> labels);

    void delete(Long issueId);
}
