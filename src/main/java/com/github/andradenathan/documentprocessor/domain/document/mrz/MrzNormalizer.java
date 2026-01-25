package com.github.andradenathan.documentprocessor.domain.document.mrz;

import io.github.hzkitty.entity.OcrResult;

public interface MrzNormalizer {
  String normalizeFromOcrResult(OcrResult ocrResult);
}
