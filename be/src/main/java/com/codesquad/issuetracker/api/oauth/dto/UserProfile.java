package com.codesquad.issuetracker.api.oauth.dto;

import com.codesquad.issuetracker.api.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private final String email;
    private final String name;
    private final String imageUrl;

    @Builder
    public UserProfile(String email, String name, String imageUrl) {
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
