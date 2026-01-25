package com.github.andradenathan.documentprocessor.domain.document.mrz;

import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;
import java.util.Optional;

public interface MrzParser {
  Optional<MrzData> parse(String mrzText);
}
