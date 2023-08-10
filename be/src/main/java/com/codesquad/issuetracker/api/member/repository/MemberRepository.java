package com.codesquad.issuetracker.api.member.repository;

import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.member.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Long> save(Member member, String providerName);

    List<MemberFilter> findFiltersBy(Long organizationId);

    Optional<Long> findBy(String email);

    Optional<Member> findMemberBy(String email);

    boolean existsNickname(String nickname);

}
