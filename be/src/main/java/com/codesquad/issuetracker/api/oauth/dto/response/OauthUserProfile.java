package com.codesquad.issuetracker.api.oauth.dto.response;

import com.codesquad.issuetracker.api.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserProfile {
    private final String id;
    private final String name;
    private final String imageUrl;

    @Builder
    public OauthUserProfile(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.id)
                .nickname(this.name)
                .profileImgUrl(this.imageUrl)
                .build();
    }

}
