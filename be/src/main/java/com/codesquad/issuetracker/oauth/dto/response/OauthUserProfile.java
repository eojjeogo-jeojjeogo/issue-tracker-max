package com.codesquad.issuetracker.oauth.dto.response;

import com.codesquad.issuetracker.api.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserProfile {
    private final String email;
    private final String name;
    private final String imageUrl;

    @Builder
    public OauthUserProfile(String email, String name, String imageUrl) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .nickname(this.name)
                .profileImgUrl(this.imageUrl)
                .build();
    }

}
