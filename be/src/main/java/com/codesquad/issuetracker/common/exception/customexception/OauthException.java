package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum OauthException implements CustomException {

    ACCESS_TOKEN_FETCH_EXCEPTION(HttpStatus.BAD_REQUEST, "엑세스 코드로 토큰을 가져오는데 실패했습니다."),
    USER_INFO_FETCH_EXCEPTION(HttpStatus.BAD_REQUEST, "인증서버로부터 사용자 정보를 가져오는데 실패했습니다.");

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
