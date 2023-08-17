package com.codesquad.issuetracker.common.exception;

import com.codesquad.issuetracker.common.exception.customexception.CustomException;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class CustomRuntimeException extends RuntimeException {
    public static final String ERROR_TYPE = "ErrorType";
    public static final String ERROR_MESSAGE = "errorMessage";
    private final CustomException customException;

    public CustomRuntimeException(CustomException customException) {
        this.customException = customException;
    }

    public ResponseEntity<Map<String, String>> sendError() {
        return ResponseEntity.status(customException.httpStatus())
                .body(Map.of(ERROR_TYPE, customException.getName(), ERROR_MESSAGE, customException.errorMessage()));
    }
}
