package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractionResult;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzExtractor;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzParser;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class MrzProcessorService {
  private final MrzExtractor mrzExtractor;
  private final MrzParser mrzParser;

  public MrzProcessorService(MrzExtractor mrzExtractor, MrzParser mrzParser) {
    this.mrzExtractor = mrzExtractor;
    this.mrzParser = mrzParser;
  }

  public Optional<Document> process(MultipartFile file) {
    try {
      Objects.requireNonNull(file);

      MrzExtractionResult extraction = mrzExtractor.extract(file);
      if (extraction.mrzText().isBlank()) return Optional.empty();

      return mrzParser.parse(extraction.mrzText());
    } catch (Exception exception) {
      log.warn("MRZ processing failed: {}", exception.getMessage(), exception);
      return Optional.empty();
    }
  }
}
