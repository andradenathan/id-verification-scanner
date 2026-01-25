package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MrzTextUtils {
  private static final Pattern STR_RES =
      Pattern.compile("strRes='(.*?)',\\s*recRes=", Pattern.DOTALL);

  private MrzTextUtils() {}

  static List<String> extractLinesFromOcrToString(String ocrToString) {
    if (ocrToString == null) return List.of();
    Matcher m = STR_RES.matcher(ocrToString);
    if (!m.find()) return List.of();
    String raw = m.group(1);
    String[] split = raw.replace("\r\n", "\n").split("\n");
    List<String> out = new ArrayList<>();
    for (String s : split) {
      String t = s == null ? "" : s.trim();
      if (!t.isBlank()) out.add(t);
    }
    return out;
  }

  static String normalizeMrzLine(String line) {
    if (line == null) return "";
    return line.toUpperCase()
        .replace('人', '<')
        .replace('|', '<')
        .replace('«', '<')
        .replace('›', '<')
        .replace('»', '<')
        .replace("AA", "<<")
        .replaceAll("\\s+", "")
        .replaceAll("[^A-Z0-9<]", "");
  }

  static String padOrTrim(String s, int len) {
    if (s == null) s = "";
    if (s.length() == len) return s;
    if (s.length() > len) return s.substring(0, len);
    return s + "<".repeat(len - s.length());
  }

  static String parseNameFromLine1(String line1) {
    if (line1 == null) return "";
    String rawNames = line1.length() > 5 ? line1.substring(5) : line1;
    return rawNames.replace('<', ' ').trim().replaceAll("\\s+", " ");
  }
}
