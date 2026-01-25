package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzParser;
import com.github.andradenathan.documentprocessor.domain.document.service.DocumentTypeResolverService;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Td3MrzParser implements MrzParser {

  @Override
  public Optional<Document> parse(String mrzText) {
    if (mrzText == null || mrzText.isBlank()) return Optional.empty();

    String[] lines = mrzText.split("\n");
    if (lines.length < 2) return Optional.empty();

    String line1 = MrzTextUtils.padOrTrim(lines[0], 44);
    String line2 = MrzTextUtils.padOrTrim(lines[1], 44);

    try {
      String rawType = line1.substring(0, 2);
      String type = DocumentTypeResolverService.resolve(rawType).description();
      String number = line2.substring(0, 9).replace("<", "");
      String nationality = line2.substring(10, 13);
      String birth = line2.substring(13, 19);
      String expiry = line2.substring(21, 27);
      String name = MrzTextUtils.parseNameFromLine1(line1);

      return Optional.of(
          new Document(line1 + "\n" + line2, name, number, type, birth, expiry, nationality));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }
}
