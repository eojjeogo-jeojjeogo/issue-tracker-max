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
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.MemberException;
import com.codesquad.issuetracker.common.exception.customexception.SignInException;
import com.codesquad.issuetracker.common.exception.customexception.SignUpException;
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
                .orElseGet(
                        () -> memberRepository.save(member, providerName).orElseThrow(() -> new CustomRuntimeException(
                                MemberException.MEMBER_SAVE_FAIL)));
        Jwt token = jwtService.issueToken(memberId);
        return SignInResponse.of(memberId, member, token);
    }

    public Long signUp(SignUpRequest signUpRequest, String providerName) {
        validateEmail(signUpRequest.getEmail());
        validateNickname(signUpRequest.getNickname());

        Member member = signUpRequest.toEntity();
        return memberRepository.save(member, providerName)
                .orElseThrow(() -> new CustomRuntimeException(
                        MemberException.MEMBER_SAVE_FAIL));
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
                    throw new CustomRuntimeException(SignUpException.INVALID_SIGN_UP_EMAIL);
                });
    }

    private void validateNickname(String nickname) {
        if (memberRepository.isNicknameExists(nickname)) {
            throw new CustomRuntimeException(SignUpException.INVALID_SIGN_UP_NICKNAME);
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(SignInException.INCORRECT_SIGN_IN_EMAIL));
    }

    private void validatePassword(SignInRequest signInRequest, Member member) {
        if (!signInRequest.validatePassword(member)) {
            throw new CustomRuntimeException(SignInException.INCORRECT_SIGN_IN_PASSWORD);
        }
    }

    private Optional<Long> getMemberId(Member member) {
        return memberRepository.findMemberIdByEmail(member.getEmail());
    }

}
