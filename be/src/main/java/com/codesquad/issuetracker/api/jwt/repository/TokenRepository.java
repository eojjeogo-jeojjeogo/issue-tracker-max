package com.codesquad.issuetracker.api.jwt.repository;

import java.util.Optional;

public interface TokenRepository {

    void saveRefreshToken(Long memberId, String refreshToken);

    void deleteRefreshToken(Long memberId);

    Optional<Long> findMemberIdBy(String refreshToken);
}
