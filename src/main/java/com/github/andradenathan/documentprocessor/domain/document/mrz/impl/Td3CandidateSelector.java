package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzCandidateSelector;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Td3CandidateSelector implements MrzCandidateSelector {
  private final Td3MrzScorer scorer = new Td3MrzScorer();

  @Override
  public Optional<String> selectBestTd3(List<String> ocrLines) {
    if (ocrLines == null || ocrLines.isEmpty()) return Optional.empty();

    List<String> normalized =
        ocrLines.stream()
            .map(MrzTextUtils::normalizeMrzLine)
            .filter(line -> line.length() >= 30)
            .toList();

    if (normalized.size() < 2) return Optional.empty();

    List<Candidate> candidates = new ArrayList<>();
    for (int i = 0; i < normalized.size(); i++) {
      for (int j = i + 1; j < normalized.size(); j++) {
        String l1 = MrzTextUtils.padOrTrim(normalized.get(i), 44);
        String l2 = MrzTextUtils.padOrTrim(normalized.get(j), 44);
        int score = scorer.score(l1, l2);
        candidates.add(new Candidate(l1, l2, score));
      }
    }

    Candidate best =
        candidates.stream().max(Comparator.comparingInt(Candidate::score)).orElse(null);
    if (best == null || best.score < 10) return Optional.empty();

    return Optional.of(best.l1 + "\n" + best.l2);
  }

  private record Candidate(String l1, String l2, int score) {}
}
