package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MilestoneException implements CustomException {
    MILESTONE_SAVE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "마일스톤 저장에 실패했습니다."),
    MILESTONE_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 마일스톤 입니다.");

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
