package com.codesquad.issuetracker.api.oauth.dto;

import com.codesquad.issuetracker.api.oauth.jwt.Jwt;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthSignInResponse {

    private Jwt tokens;

    @Builder
    public OauthSignInResponse(Jwt tokens) {
        this.tokens = tokens;
    }
}
