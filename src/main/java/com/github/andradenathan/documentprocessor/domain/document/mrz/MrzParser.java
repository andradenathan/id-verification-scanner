package com.github.andradenathan.documentprocessor.domain.document.mrz;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import java.util.Optional;

public interface MrzParser {
  Optional<Document> parse(String mrzText);
}
