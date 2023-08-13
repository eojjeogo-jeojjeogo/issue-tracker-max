package com.codesquad.issuetracker.api.member.service;

import com.codesquad.issuetracker.api.member.domain.Member;
import com.codesquad.issuetracker.api.member.dto.request.RefreshTokenRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignInRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignUpRequest;
import com.codesquad.issuetracker.api.member.dto.response.SignInResponse;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.member.repository.TokenRepository;
import com.codesquad.issuetracker.api.oauth.dto.response.OauthUserProfile;
import com.codesquad.issuetracker.api.oauth.service.OauthService;
import com.codesquad.issuetracker.jwt.Jwt;
import com.codesquad.issuetracker.jwt.JwtProvider;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final OauthService oauthService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    public SignInResponse oAuthSignIn(String providerName, String code) {
        //github에서 사용자 정보 가져오기
        OauthUserProfile oauthUserProfile = oauthService.getOauthUserProfile(providerName, code);
        Member member = oauthUserProfile.toEntity();

        Optional<Long> memberId = memberRepository.findBy(member.getEmail());
        if (!memberId.isPresent()) {
            memberId = memberRepository.save(member, providerName);
        }
        Jwt tokens = jwtProvider.createTokens(Map.of("memberId", memberId.get()));
        tokenRepository.deleteRefreshToken(memberId.get());

        tokenRepository.save(memberId.get(), tokens.getRefreshToken());
        return SignInResponse.of(memberId.get(), member, tokens);
    }

    public Long signUp(SignUpRequest signUpRequest, String providerName) {
        //member 테이블에 저장된 email 인지 또는 저장된 nickname인지 확인
        if (memberRepository.findBy(signUpRequest.getEmail()).isPresent()) {
            // 예외처리
        }

        //member 테이블에 저장된 nickname인지 확인
        if (memberRepository.existsNickname(signUpRequest.getNickname())) {
            //예외처리
        }

        //member 테이블에 저장
        Member member = signUpRequest.toEntity();
        Long memberId = memberRepository.save(member, providerName).orElseThrow();

        return memberId;
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        //해당 email의 회원이 없다면 "잘못된 이메일 입니다" 예외 던지기
        Member member = memberRepository.findMemberBy(signInRequest.getEmail()).orElseThrow();

        if (signInRequest.validatePassword(member)) {
            // 비밀번호 다르다는 예외처리
        }
        Jwt tokens = jwtProvider.createTokens(Map.of("memberId", member.getId()));

        tokenRepository.deleteRefreshToken(member.getId());
        tokenRepository.save(member.getId(), tokens.getRefreshToken());

        return SignInResponse.of(member, tokens);
    }

    public String reissueAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<Long> memberId = tokenRepository.findMemberIdBy(refreshTokenRequest.getRefreshToken());

        Jwt tokens = jwtProvider.createTokens(Map.of("memberId", memberId.get()));

        return tokens.getAccessToken();
    }

}
