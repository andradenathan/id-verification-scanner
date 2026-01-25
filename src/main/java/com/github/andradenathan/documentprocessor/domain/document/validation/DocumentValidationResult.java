package com.github.andradenathan.documentprocessor.domain.document.validation;

import java.util.List;

public record DocumentValidationResult(boolean valid, List<DocumentValidationIssue> issues) {
  public static DocumentValidationResult ok() {
    return new DocumentValidationResult(true, List.of());
  }

  public static DocumentValidationResult fail(List<DocumentValidationIssue> issues) {
    return new DocumentValidationResult(false, issues);
  }
}
