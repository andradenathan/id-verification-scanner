package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzCandidateSelector;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzNormalizer;
import io.github.hzkitty.entity.OcrResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Td3MrzNormalizer implements MrzNormalizer {
  private final MrzCandidateSelector selector;

  public Td3MrzNormalizer(MrzCandidateSelector selector) {
    this.selector = selector;
  }

  @Override
  public String normalizeFromOcrResult(OcrResult ocrResult) {
    if (ocrResult == null) return "";

    String raw = ocrResult.toString();

    List<String> lines = MrzTextUtils.extractLinesFromOcrToString(raw);

    return selector.selectBestTd3(lines).orElse("");
  }
}
