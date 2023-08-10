package com.codesquad.issuetracker.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;

public enum JwtExceptionType {

    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "기한이 만료되었습니다."),
    MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "올바른 키가 아닙니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 값이 들어왔습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    JwtExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public static JwtExceptionType from(RuntimeException e) {
        if (e.getClass().equals(ExpiredJwtException.class)) {
            return JwtExceptionType.EXPIRED_JWT_EXCEPTION;
        } else if (e.getClass().equals(MalformedJwtException.class)) {
            return JwtExceptionType.MALFORMED_JWT_EXCEPTION;
        } else if (e.getClass().equals(SignatureException.class)) {
            return JwtExceptionType.SIGNATURE_EXCEPTION;
        }
        return JwtExceptionType.ILLEGAL_ARGUMENT_EXCEPTION;
    }
}
