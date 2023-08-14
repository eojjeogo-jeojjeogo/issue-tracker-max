package com.codesquad.issuetracker.api.member.service;

import com.codesquad.issuetracker.api.jwt.domain.Jwt;
import com.codesquad.issuetracker.api.jwt.service.JwtService;
import com.codesquad.issuetracker.api.member.domain.Member;
import com.codesquad.issuetracker.api.member.dto.request.SignInRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignUpRequest;
import com.codesquad.issuetracker.api.member.dto.response.SignInResponse;
import com.codesquad.issuetracker.api.member.repository.MemberRepository;
import com.codesquad.issuetracker.api.oauth.dto.response.OauthUserProfile;
import com.codesquad.issuetracker.api.oauth.service.OauthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final OauthService oauthService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public SignInResponse oAuthSignIn(String providerName, String code) {
        OauthUserProfile oauthUserProfile = oauthService.getOauthUserProfile(providerName, code);
        Member member = oauthUserProfile.toEntity();
        Long memberId = getMemberId(member)
                .orElseGet(() -> memberRepository.save(member, providerName).orElseThrow());
        Jwt token = jwtService.issueToken(memberId);
        return SignInResponse.of(memberId, member, token);
    }

    public Long signUp(SignUpRequest signUpRequest, String providerName) {
        validateEmail(signUpRequest.getEmail());
        validateNickname(signUpRequest.getNickname());

        Member member = signUpRequest.toEntity();
        return memberRepository.save(member, providerName)
                .orElseThrow(); // 회원정보 저장 실패 예외
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        Member member = findMemberByEmail(signInRequest.getEmail());
        validatePassword(signInRequest, member);
        Jwt tokens = jwtService.issueToken(member.getId());

        return SignInResponse.of(member, tokens);
    }

    private void validateEmail(String email) {
        memberRepository.findMemberIdByEmail(email)
                .ifPresent(member -> {
                    //이메일이 다를때 예외 던지기
                });
    }

    private void validateNickname(String nickname) {
        if (memberRepository.isNicknameExists(nickname)) {
            // 닉네임이 존재할때 예외 던지기
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(); // () -> new InvalidEmailException() 해당 email을 가진 사용자가 없으면 예외 던지기
    }

    private void validatePassword(SignInRequest signInRequest, Member member) {
        if (!signInRequest.validatePassword(member)) {
            // 패스워드가 다르다는 예외 던지기
        }
    }

    private Optional<Long> getMemberId(Member member) {
        return memberRepository.findMemberIdByEmail(member.getEmail());
    }

}
