package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum SignInException implements CustomException {
    INCORRECT_SIGN_IN_EMAIL(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 입니다."),
    INCORRECT_SIGN_IN_PASSWORD(HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return message;
    }

    @Override
    public String getName() {
        return name();
    }
}
