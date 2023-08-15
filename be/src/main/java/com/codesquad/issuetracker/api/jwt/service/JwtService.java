package com.codesquad.issuetracker.api.jwt.service;

import com.codesquad.issuetracker.api.jwt.domain.Jwt;
import com.codesquad.issuetracker.api.jwt.repository.TokenRepository;
import com.codesquad.issuetracker.api.member.dto.request.RefreshTokenRequest;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.JwtException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String MEMBER_ID = "memberId";
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    public Jwt issueToken(Long memberId) {
        Jwt token = jwtProvider.createTokens(Map.of(MEMBER_ID, memberId));
        tokenRepository.deleteRefreshToken(memberId);
        tokenRepository.saveRefreshToken(memberId, token.getRefreshToken());
        return token;
    }

    public String reissueAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Long memberId = tokenRepository.findMemberIdBy(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new CustomRuntimeException(JwtException.REFRESH_TOKEN_NOT_FOUND_EXCEPTION));
        Jwt tokens = jwtProvider.createTokens(Map.of(MEMBER_ID, memberId));
        return tokens.getAccessToken();
    }

}
