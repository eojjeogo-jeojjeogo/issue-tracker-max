package com.codesquad.issuetracker.api.member.dto.request;

import com.codesquad.issuetracker.api.member.domain.Member;
import lombok.Getter;

@Getter
public class SignInRequest {

    private String email;
    private String password;

    public boolean validatePassword(Member member) {
        return this.password.equals(member.getPassword());
    }
}
