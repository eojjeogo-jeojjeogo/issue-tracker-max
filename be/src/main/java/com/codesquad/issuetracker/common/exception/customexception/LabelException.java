package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum LabelException implements CustomException {
    LABEL_SAVE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "레이블 저장에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String errorMessage() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
