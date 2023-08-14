package com.codesquad.issuetracker.api.member.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDateTime createdTime;
    private final String profileImgUrl;
    private final Long signInTypeId;
}
