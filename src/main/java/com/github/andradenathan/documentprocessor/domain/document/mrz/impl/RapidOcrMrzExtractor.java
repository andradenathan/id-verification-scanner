package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractor;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzNormalizer;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzPreprocessor;
import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzExtractionResult;
import io.github.hzkitty.RapidOCR;
import io.github.hzkitty.entity.OcrResult;
import java.io.File;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RapidOcrMrzExtractor implements MrzExtractor {
  private final RapidOCR ocr;
  private final MrzPreprocessor preprocessor;
  private final MrzNormalizer normalizer;

  public RapidOcrMrzExtractor(MrzPreprocessor preprocessor, MrzNormalizer normalizer) {
    this.ocr = RapidOCR.create();
    this.preprocessor = preprocessor;
    this.normalizer = normalizer;
  }

  @Override
  public MrzExtractionResult extract(File file) {
    Objects.requireNonNull(file, "file must not be null");

    try {
      File mrzCrop = preprocessor.cropToMrzBand(file);

      OcrResult result = ocr.run(mrzCrop.getAbsolutePath());
      String mrzText = normalizer.normalizeFromOcrResult(result);

      return mrzText.isBlank() ? MrzExtractionResult.empty() : new MrzExtractionResult(mrzText);
    } catch (Exception exception) {
      log.warn("MRZ OCR extraction failed: {}", exception.getMessage(), exception);
      return MrzExtractionResult.empty();
    }
  }
}
