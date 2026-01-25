package com.github.andradenathan.documentprocessor.domain.document.mrz;

public record MrzExtractionResult(String mrzText) {
  public static MrzExtractionResult empty() {
    return new MrzExtractionResult("");
  }
}
