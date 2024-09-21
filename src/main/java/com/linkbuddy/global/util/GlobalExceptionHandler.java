package com.linkbuddy.global.util;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : GlobalExceptionHandler
 * author         : yl951
 * date           : 2024-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-12        yl951       최초 생성
 */

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Object> handleCustomException(CustomException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatusEnum().statusCode,
            ex.getStatusEnum().code,
            ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusEnum().code));

  }

  @Getter
  static class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponse(int statusCode, String code, String message) {
      this.status = statusCode;
      this.code = code;
      this.message = message;
    }

    // Getters and setters
  }
}
