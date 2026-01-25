package com.github.andradenathan.documentprocessor.domain.document.mrz;

import java.io.File;

public interface MrzPreprocessor {
  File cropToMrzBand(File inputImageFile);
}
