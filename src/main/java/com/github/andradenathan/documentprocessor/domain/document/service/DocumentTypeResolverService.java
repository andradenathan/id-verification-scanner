package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.entity.DocumentType;
import java.util.HashMap;
import java.util.Map;

public class DocumentTypeResolverService {
  private static final Map<String, DocumentType> BY_CODE = new HashMap<>();

  static {
    for (DocumentType type : DocumentType.values()) {
      BY_CODE.put(type.code(), type);
    }
  }

  private DocumentTypeResolverService() {}

  public static DocumentType resolve(String mrzTypeCode) {
    if (mrzTypeCode == null || mrzTypeCode.isBlank()) {
      return DocumentType.UNKNOWN;
    }

    String normalized = mrzTypeCode.toUpperCase().replace("<", "");

    if (normalized.length() >= 2) {
      String full = normalized.substring(0, 2);
      if (BY_CODE.containsKey(full)) {
        return BY_CODE.get(full);
      }
    }

    String primary = normalized.substring(0, 1);
    return BY_CODE.getOrDefault(primary, DocumentType.UNKNOWN);
  }
}
