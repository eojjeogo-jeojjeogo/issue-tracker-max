package com.codesquad.issuetracker.api.oauth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Jwt {

    private final String accessToken;
    private final String refreshToken;

}
