package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractionResult;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractor;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzNormalizer;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzPreprocessor;
import io.github.hzkitty.RapidOCR;
import io.github.hzkitty.entity.OcrResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
  public MrzExtractionResult extract(MultipartFile file) {
    Objects.requireNonNull(file);

    try {
      File input = toTempFile(file);
      File mrzCrop = preprocessor.cropToMrzBand(input);

      OcrResult result = ocr.run(mrzCrop.getAbsolutePath());
      String mrzText = normalizer.normalizeFromOcrResult(result);

      return mrzText.isBlank() ? MrzExtractionResult.empty() : new MrzExtractionResult(mrzText);
    } catch (Exception e) {
      log.warn("MRZ OCR extraction failed: {}", e.getMessage(), e);
      return MrzExtractionResult.empty();
    }
  }

  private File toTempFile(MultipartFile file) throws Exception {
    Path path =
        Files.createTempFile("uploaded-doc-", Objects.toString(file.getOriginalFilename(), "file"));
    File out = path.toFile();
    file.transferTo(out);
    return out;
  }
}
