package com.codesquad.issuetracker.api.member.dto.request;

import com.codesquad.issuetracker.api.member.domain.Member;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignInRequest {

    @NotNull
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotNull
    @Size(min = 6, max = 12, message = "유효하지 않은 비밀번호 형식입니다.")
    private String password;

    public boolean validatePassword(Member member) {
        return this.password.equals(member.getPassword());
    }
}
