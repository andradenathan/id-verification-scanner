package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import java.time.LocalDate;

public class MrzDateParser {
  public static LocalDate parse(String yymmdd) {
    if (yymmdd == null || yymmdd.length() != 6) return null;

    try {
      int year = Integer.parseInt(yymmdd.substring(0, 2));
      int month = Integer.parseInt(yymmdd.substring(2, 4));
      int day = Integer.parseInt(yymmdd.substring(4, 6));

      int fullYear = (year <= 40) ? (2000 + year) : (1900 + year);
      return LocalDate.of(fullYear, month, day);
    } catch (Exception exception) {
      return null;
    }
  }
}
