package com.codesquad.issuetracker.common.exception.customexception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum IssueException implements CustomException {
    ISSUE_SAVE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "레이블 저장에 실패했습니다."),
    ISSUE_TITLE_UPDATE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "레이블 제목 수정에 실패했습니다."),
    ISSUE_MILESTONE_UPDATE_FAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "레이블 수정에 실패했습니다.");

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
