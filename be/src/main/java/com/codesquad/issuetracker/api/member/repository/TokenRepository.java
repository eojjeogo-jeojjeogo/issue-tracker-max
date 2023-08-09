package com.codesquad.issuetracker.api.member.repository;

public interface TokenRepository {

    void save(Long memberId, String refreshToken);
}
