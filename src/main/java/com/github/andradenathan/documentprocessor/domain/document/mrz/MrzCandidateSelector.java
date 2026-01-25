package com.github.andradenathan.documentprocessor.domain.document.mrz;

import java.util.List;
import java.util.Optional;

public interface MrzCandidateSelector {
  Optional<String> selectBestTd3(List<String> ocrLines);
}
