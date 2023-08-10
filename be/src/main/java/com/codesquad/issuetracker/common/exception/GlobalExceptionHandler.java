package com.codesquad.issuetracker.common.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, String>> malformedJwtException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errorMessage", "에러메세지malformed"));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> JwtException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errorMessage", "에러메세지jwt"));
    }
}
