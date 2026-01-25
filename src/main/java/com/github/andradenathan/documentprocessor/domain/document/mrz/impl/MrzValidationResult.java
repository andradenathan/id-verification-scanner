package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import java.util.List;

public class MrzValidationResult {
  public List<String> errors;

  public MrzValidationResult(List<String> errors) {
    this.errors = errors;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }
}
