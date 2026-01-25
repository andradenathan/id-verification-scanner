package com.github.andradenathan.documentprocessor.domain.document.validation.mrz;

import java.util.Map;

public class SexResolver {
  private static final Map<String, String> SEX =
      Map.of(
          "M", "Masculine",
          "F", "Feminine",
          "X", "Not specified",
          "<", "Not specified");

  private SexResolver() {}

  public static boolean isValid(String raw) {
    if (raw == null || raw.isBlank()) return false;
    return SEX.containsKey(raw.trim().toUpperCase());
  }

  public static String nameOf(String raw) {
    if (raw == null || raw.isBlank()) return "Not specified";
    return SEX.getOrDefault(raw.trim().toUpperCase(), "Not specified");
  }
}
