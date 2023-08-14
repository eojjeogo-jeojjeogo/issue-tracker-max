package com.codesquad.issuetracker.common.exception.customexception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus httpStatus();

    String errorMessage();

    String getName();
}
