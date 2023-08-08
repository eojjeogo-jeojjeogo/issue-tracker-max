package com.codesquad.issuetracker.api.member.repository;

import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import java.util.List;

public interface MemberRepository {

    List<MemberFilter> findFiltersBy(Long organizationId);
}
