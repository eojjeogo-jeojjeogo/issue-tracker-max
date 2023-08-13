package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueFilterVo;
import com.codesquad.issuetracker.api.issue.dto.IssueFilterRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IssueFilterMapperImpl implements IssueFilterMapper {

    private final IssueFilterMapper issueFilterMapper;

    @Override
    public List<IssueFilterVo> readAll(IssueFilterRequest issueFilterRequest, Long organizationId) {
        return issueFilterMapper.readAll(issueFilterRequest, organizationId);
    }
}
