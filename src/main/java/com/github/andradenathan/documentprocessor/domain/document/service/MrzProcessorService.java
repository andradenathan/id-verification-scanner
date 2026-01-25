package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractor;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzParser;
import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;
import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzExtractionResult;
import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationResult;
import com.github.andradenathan.documentprocessor.domain.document.validation.mrz.MrzTd3Validator;
import java.io.File;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MrzProcessorService {
  private final MrzExtractor mrzExtractor;
  private final MrzParser mrzParser;
  private final MrzTd3Validator mrzTd3Validator;

  public MrzProcessorService(
      MrzExtractor mrzExtractor, MrzParser mrzParser, MrzTd3Validator mrzTd3Validator) {
    this.mrzExtractor = mrzExtractor;
    this.mrzParser = mrzParser;
    this.mrzTd3Validator = mrzTd3Validator;
  }

  public ProcessDocumentResponse process(File file) {
    try {
      Objects.requireNonNull(file);

      MrzExtractionResult extraction = mrzExtractor.extract(file);
      if (extraction.mrzText().isBlank()) return ProcessDocumentResponse.empty();

      Optional<MrzData> mrzDataOptional = mrzParser.parse(extraction.mrzText());
      if (mrzDataOptional.isEmpty()) return ProcessDocumentResponse.empty();

      DocumentValidationResult validationResult = mrzTd3Validator.validate(mrzDataOptional.get());

      Document document = MrzData.toDocument(mrzDataOptional.get());

      return ProcessDocumentResponse.of(document, validationResult.issues());
    } catch (Exception exception) {
      log.warn("MRZ processing failed: {}", exception.getMessage(), exception);
      return ProcessDocumentResponse.empty();
    }
  }
}
