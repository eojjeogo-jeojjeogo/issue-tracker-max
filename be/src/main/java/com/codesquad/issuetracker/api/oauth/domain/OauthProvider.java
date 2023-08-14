package com.codesquad.issuetracker.api.oauth.domain;

import com.codesquad.issuetracker.api.oauth.config.OauthProperties.Provider;
import com.codesquad.issuetracker.api.oauth.config.OauthProperties.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthProvider {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public OauthProvider(User user, Provider provider) {
        this(user.getClientId(), user.getClientSecret(), user.getRedirectUri(), provider.getTokenUri(),
                provider.getUserInfoUri());
    }

    @Builder
    public OauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl,
                         String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }
}
