package com.github.andradenathan.documentprocessor.domain.document.mrz;

import org.springframework.web.multipart.MultipartFile;

public interface MrzExtractor {
  MrzExtractionResult extract(MultipartFile file);
}
