package com.codesquad.issuetracker.common.exception.handler;

import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<Map<String, String>> customExceptionHandler(CustomRuntimeException e) {
        log.info("api 예외발생! errorType: " + e.getCustomException().getName() + " errorMessage: " + e.getCustomException()
                .errorMessage());
        return e.sendError();
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<Map<String, String>> incorrectResultSizeDataAccessExceptionHandler(
            IncorrectResultSizeDataAccessException e) {
        log.info("sql 예외발생! expectedSize: " + e.getExpectedSize() + " actualSize: " + e.getActualSize());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("errorMessage", "DB에서 예외가 발생했습니다."));
    }
}
