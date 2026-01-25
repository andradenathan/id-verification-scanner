package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationIssue;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationResult;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentValidatorService<T> {
  private final List<DocumentValidator<T>> validators;

  public DocumentValidatorService(List<DocumentValidator<T>> validators) {
    this.validators = validators;
  }

  public DocumentValidationResult validate(T document) {
    List<DocumentValidationIssue> issues =
        validators.stream()
            .filter(validator -> validator.supports(document))
            .map(validator -> validator.validate(document))
            .flatMap(result -> result.issues().stream())
            .toList();

    return issues.isEmpty() ? DocumentValidationResult.ok() : DocumentValidationResult.fail(issues);
  }
}
