package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import java.util.List;

public interface IssueLabelRepository {

    List<IssueLabelVo> findAllBy(Long issueId);
}
