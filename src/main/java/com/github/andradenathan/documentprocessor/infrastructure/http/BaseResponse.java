package com.github.andradenathan.documentprocessor.infrastructure.http;

public record BaseResponse(Object result, String message) {
  public static BaseResponse success(Object result) {
    return new BaseResponse(result, "success");
  }
}
