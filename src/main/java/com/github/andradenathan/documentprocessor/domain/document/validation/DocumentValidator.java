package com.github.andradenathan.documentprocessor.domain.document.validation;

public interface DocumentValidator<T> {
  boolean supports(T document);

  DocumentValidationResult validate(T document);
}
