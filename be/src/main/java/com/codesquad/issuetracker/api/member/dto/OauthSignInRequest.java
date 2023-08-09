package com.codesquad.issuetracker.api.member.dto;

import lombok.Getter;

@Getter
public class OauthSignInRequest {

    public OauthSignInRequest() {
    }

    private String accessCode;

}
