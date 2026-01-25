package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

final class Td3MrzScorer {
  private static final int[] W = {7, 3, 1};

  int score(String l1, String l2) {
    int score = 0;

    if (l1.length() == 44) score += 2;
    if (l2.length() == 44) score += 2;

    if (!l1.isEmpty() && Character.isLetter(l1.charAt(0))) score += 2;

    String country = l1.substring(2, 5);
    if (country.matches("[A-Z]{3}")) score += 2;

    if (checkDigitMatches(l2, 0, 9, 9)) score += 10;
    if (checkDigitMatches(l2, 13, 19, 19)) score += 10;
    if (checkDigitMatches(l2, 21, 27, 27)) score += 10;
    if (compositeCheckMatches(l2)) score += 15;

    return score;
  }

  private boolean checkDigitMatches(String line2, int start, int end, int checkPos) {
    if (line2 == null || line2.length() <= checkPos) return false;
    char expected = computeCheckDigit(line2.substring(start, end));
    char actual = line2.charAt(checkPos);
    return Character.isDigit(actual) && actual == expected;
  }

  private boolean compositeCheckMatches(String line2) {
    if (line2 == null || line2.length() < 44) return false;
    String composite =
        line2.substring(0, 10)
            + line2.substring(13, 20)
            + line2.substring(21, 28)
            + line2.substring(28, 43);
    char expected = computeCheckDigit(composite);
    char actual = line2.charAt(43);
    return Character.isDigit(actual) && actual == expected;
  }

  private char computeCheckDigit(String input) {
    int sum = 0;
    for (int i = 0; i < input.length(); i++) {
      sum += value(input.charAt(i)) * W[i % 3];
    }
    return (char) ('0' + (sum % 10));
  }

  private int value(char c) {
    if (c >= '0' && c <= '9') return c - '0';
    if (c >= 'A' && c <= 'Z') return c - 'A' + 10;
    if (c == '<') return 0;
    return 0;
  }
}
