package com.codesquad.issuetracker.api.member.Service;

import com.codesquad.issuetracker.api.member.domain.Member;
import com.codesquad.issuetracker.api.member.dto.SignUpRequest;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.member.repository.TokenRepository;
import com.codesquad.issuetracker.api.oauth.dto.OauthSignInResponse;
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

    // steps:
    // 1. DB 조회해서 회원 여부 판단
    // 2. 회원이면 member 데이터랑 대조해서 토큰 발급해주고
    // 3. 회원이 아니면 깃허브에 액세스 토큰 요청하고 프론트로 전달
    // 4. 프론트가 액세스 토큰을 전달주면 백에서 회원가입 시키고 액세스/리프레시 토큰 발급

    // LoginRequest = 이메일, 비밀번호
    // DB -> 있어? 로그인
    // DB -> 없어 -> 액세스 토큰 : 유저 정보를 DB에 저장 -> 로그인

    private final OauthService oauthService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    public OauthSignInResponse oAuthSignIn(String providerName, String code) {
        //github에서 사용자 정보 가져오기
        UserProfile userProfile = oauthService.login(providerName, code);
        Member member = userProfile.toEntity();

        Optional<Long> memberId = memberRepository.findBy(member.getEmail());
        if (!memberId.isPresent()) {
            memberId = memberRepository.save(member, providerName);
        }
        // 토큰 발급
        Jwt tokens = jwtProvider.createTokens(Map.of("memberId", memberId));

        //todo 로그인을 할때마다 사용자에게 새로운 refresh 토큰을 만들어줄거면 DB에 저장된 refresh 토큰은 삭제해야할듯?
        if (memberId.isPresent()) {
            tokenRepository.deleteRefreshToken(memberId.get());
        }

        // refresh토큰 저장
        tokenRepository.save(memberId.get(), tokens.getRefreshToken());
        return new OauthSignInResponse(tokens);
    }

    public Long signUp(SignUpRequest signUpRequest, String providerName) {
        //member 테이블에 저장된 email 인지 또는 저장된 nickname인지 확인
        if (memberRepository.findBy(signUpRequest.getEmail()).isPresent()) {
            // 예외처리
        }

        Boolean isNicknameExists = memberRepository.existsNickname(signUpRequest.getNickname());
        if (isNicknameExists) {
            //예외처리
        }

        //member 테이블에 저장
        Member member = signUpRequest.toEntity();
        Long memberId = memberRepository.save(member, providerName).orElseThrow();

        return memberId;
    }

}
