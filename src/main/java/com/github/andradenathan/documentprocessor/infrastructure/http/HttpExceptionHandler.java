package com.github.andradenathan.documentprocessor.infrastructure.http;

import java.io.IOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler(IOException.class)
  public BaseResponse handleIoException(IOException ioException) {
    return new BaseResponse(null, "IO Error: " + ioException.getMessage());
  }
}
