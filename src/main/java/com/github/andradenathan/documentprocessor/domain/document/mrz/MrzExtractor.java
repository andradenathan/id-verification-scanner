package com.github.andradenathan.documentprocessor.domain.document.mrz;

import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzExtractionResult;
import org.springframework.web.multipart.MultipartFile;

public interface MrzExtractor {
  MrzExtractionResult extract(MultipartFile file);
}
