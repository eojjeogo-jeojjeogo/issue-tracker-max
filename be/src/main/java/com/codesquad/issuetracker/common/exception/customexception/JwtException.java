package com.codesquad.issuetracker.common.exception.customexception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;

public enum JwtException implements CustomException {

    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "기한이 만료되었습니다."),
    MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "올바른 키가 아닙니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 값이 들어왔습니다."),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(HttpStatus.UNAUTHORIZED, "DB에 Refresh token을 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    JwtException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static JwtException from(RuntimeException e) {
        if (e.getClass().equals(ExpiredJwtException.class)) {
            return JwtException.EXPIRED_JWT_EXCEPTION;
        } else if (e.getClass().equals(MalformedJwtException.class)) {
            return JwtException.MALFORMED_JWT_EXCEPTION;
        } else if (e.getClass().equals(SignatureException.class)) {
            return JwtException.SIGNATURE_EXCEPTION;
        }
        return JwtException.ILLEGAL_ARGUMENT_EXCEPTION;
    }

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
