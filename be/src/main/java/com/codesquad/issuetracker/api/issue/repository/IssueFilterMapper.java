package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueFilterVo;
import com.codesquad.issuetracker.api.issue.dto.IssueFilterRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IssueFilterMapper {

    List<IssueFilterVo> readAll(@Param("request") IssueFilterRequest issueFilterRequest,
                                @Param("organizationId") Long organizationId);
}
