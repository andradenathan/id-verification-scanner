package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;

public final class MrzDocumentMapper {
  private MrzDocumentMapper() {}

  public static Document toDocument(MrzData mrz) {

    return new Document(
        mrz.mrz(),
        mrz.fullName(),
        mrz.documentNumber(),
        mrz.documentTypeName(),
        mrz.birthDate(),
        mrz.expiryDate(),
        mrz.nationalityName(),
        mrz.sex());
  }
}
