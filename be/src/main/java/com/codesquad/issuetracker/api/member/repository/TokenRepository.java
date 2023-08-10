package com.codesquad.issuetracker.api.member.repository;

import java.util.Optional;

public interface TokenRepository {

    void save(Long memberId, String refreshToken);

    void deleteRefreshToken(Long memberId);

    Optional<Long> findMemberIdBy(String refreshToken);
}
