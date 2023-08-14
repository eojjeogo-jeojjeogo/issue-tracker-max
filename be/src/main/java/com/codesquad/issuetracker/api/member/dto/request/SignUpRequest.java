package com.codesquad.issuetracker.api.member.dto.request;

import com.codesquad.issuetracker.api.member.domain.Member;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String nickname;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }
}
