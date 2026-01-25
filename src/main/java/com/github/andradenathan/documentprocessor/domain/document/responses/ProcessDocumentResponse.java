package com.github.andradenathan.documentprocessor.domain.document.responses;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationIssue;
import java.util.List;

public record ProcessDocumentResponse(Document document, List<DocumentValidationIssue> issues) {
  public static ProcessDocumentResponse of(
      Document document, List<DocumentValidationIssue> issues) {
    return new ProcessDocumentResponse(document, issues);
  }

  public static ProcessDocumentResponse empty() {
    return new ProcessDocumentResponse(null, List.of());
  }

  public boolean isNotEmpty() {
    return document != null && !issues.isEmpty();
  }
}
