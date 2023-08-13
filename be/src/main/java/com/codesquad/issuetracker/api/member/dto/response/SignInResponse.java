package com.codesquad.issuetracker.api.member.dto.response;

import com.codesquad.issuetracker.api.member.domain.Member;
import com.codesquad.issuetracker.jwt.Jwt;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {

    private Long id;
    private String name;
    private String imgUrl;
    private Jwt tokens;

    public static SignInResponse of(Long memberId, Member member, Jwt token) {
        return SignInResponse.builder()
                .id(memberId)
                .name(member.getNickname())
                .imgUrl(member.getProfileImgUrl())
                .tokens(token)
                .build();
    }

    public static SignInResponse of(Member member, Jwt token) {
        return SignInResponse.builder()
                .id(member.getId())
                .name(member.getNickname())
                .imgUrl(member.getProfileImgUrl())
                .tokens(token)
                .build();
    }
}
