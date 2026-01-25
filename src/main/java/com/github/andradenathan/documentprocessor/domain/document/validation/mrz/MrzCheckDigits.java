package com.github.andradenathan.documentprocessor.domain.document.validation.mrz;

final class MrzCheckDigits {
  private static final int[] WEIGHTS = {7, 3, 1};

  private MrzCheckDigits() {}

  static boolean isDocumentNumberValid(String secondLine) {
    return check(secondLine, 0, 9, 9);
  }

  static boolean isBirthDateValid(String secondLine) {
    return check(secondLine, 13, 19, 19);
  }

  static boolean isExpiryDateValid(String secondLine) {
    return check(secondLine, 21, 27, 27);
  }

  static boolean isCompositionValid(String secondLine) {
    if (secondLine == null || secondLine.length() < 44) return false;
    String composite =
        secondLine.substring(0, 10)
            + secondLine.substring(13, 20)
            + secondLine.substring(21, 28)
            + secondLine.substring(28, 43);
    return compute(composite) == secondLine.charAt(43);
  }

  private static boolean check(String secondLine, int start, int end, int checkPos) {
    if (secondLine == null || secondLine.length() <= checkPos) return false;
    char expected = compute(secondLine.substring(start, end));
    char actual = secondLine.charAt(checkPos);
    return Character.isDigit(actual) && expected == actual;
  }

  private static char compute(String input) {
    int sum = 0;
    for (int i = 0; i < input.length(); i++) {
      sum += value(input.charAt(i)) * WEIGHTS[i % 3];
    }
    return (char) ('0' + (sum % 10));
  }

  private static int value(char c) {
    if (c >= '0' && c <= '9') return c - '0';
    if (c >= 'A' && c <= 'Z') return c - 'A' + 10;
    if (c == '<') return 0;
    return 0;
  }
}
