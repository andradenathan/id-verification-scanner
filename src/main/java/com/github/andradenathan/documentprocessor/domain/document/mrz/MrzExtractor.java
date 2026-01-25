package com.github.andradenathan.documentprocessor.domain.document.mrz;

import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzExtractionResult;
import java.io.File;

public interface MrzExtractor {
  MrzExtractionResult extract(File file);
}
