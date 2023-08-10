package com.codesquad.issuetracker.api.member.Service;

import com.codesquad.issuetracker.api.member.domain.Member;
import com.codesquad.issuetracker.api.member.dto.SignInRequest;
import com.codesquad.issuetracker.api.member.dto.SignUpRequest;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.member.repository.TokenRepository;
import com.codesquad.issuetracker.api.oauth.dto.SignInResponse;
import com.codesquad.issuetracker.api.oauth.dto.UserProfile;
import com.codesquad.issuetracker.api.oauth.jwt.Jwt;
import com.codesquad.issuetracker.api.oauth.jwt.JwtProvider;
import com.codesquad.issuetracker.api.oauth.service.OauthService;
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
        UserProfile userProfile = oauthService.login(providerName, code);
        Member member = userProfile.toEntity();

        Optional<Long> memberId = memberRepository.findBy(member.getEmail());
        if (!memberId.isPresent()) {
            memberId = memberRepository.save(member, providerName);
        }
        Jwt tokens = jwtProvider.createTokens(Map.of("memberId", memberId));

        if (memberId.isPresent()) {
            tokenRepository.deleteRefreshToken(memberId.get());
        }

        tokenRepository.save(memberId.get(), tokens.getRefreshToken());
        return new SignInResponse(tokens);
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

        return new SignInResponse(tokens);
    }

}
