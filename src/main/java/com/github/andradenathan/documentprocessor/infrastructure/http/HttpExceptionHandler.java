package com.github.andradenathan.documentprocessor.infrastructure.http;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public BaseResponse handleIoException(RuntimeException runtimeException) {
    return new BaseResponse(null, runtimeException.getMessage());
  }
}
