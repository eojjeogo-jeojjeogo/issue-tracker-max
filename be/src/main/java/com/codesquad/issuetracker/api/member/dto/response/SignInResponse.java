package com.codesquad.issuetracker.api.member.dto.response;

import com.codesquad.issuetracker.jwt.Jwt;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private Jwt tokens;

    @Builder
    public SignInResponse(Jwt tokens) {
        this.tokens = tokens;
    }
}
