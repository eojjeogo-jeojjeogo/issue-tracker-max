package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommentException implements CustomException {
    COMMENT_SAVE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "코멘트 저장에 실패했습니다.");

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
