package com.codesquad.issuetracker.oauth;

import com.codesquad.issuetracker.oauth.dto.response.OauthUserProfile;
import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    GITHUB("github") {
        @Override
        public OauthUserProfile of(Map<String, Object> attributes) {
            return OauthUserProfile.builder()
                    .id((String) attributes.get("login"))
                    .name((String) attributes.get("name"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .build();
        }
    };

    private final String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static OauthUserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(attributes);
    }

    public abstract OauthUserProfile of(Map<String, Object> attributes);
}
