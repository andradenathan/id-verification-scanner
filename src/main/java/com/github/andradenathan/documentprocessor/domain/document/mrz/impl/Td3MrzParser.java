package com.github.andradenathan.documentprocessor.domain.document.mrz.impl;

import com.github.andradenathan.documentprocessor.domain.document.mrz.MrzParser;
import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;
import com.github.andradenathan.documentprocessor.domain.document.validation.mrz.CountryCodeResolver;
import com.github.andradenathan.documentprocessor.domain.document.validation.mrz.DocumentTypeResolver;
import com.github.andradenathan.documentprocessor.domain.document.validation.mrz.SexResolver;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Td3MrzParser implements MrzParser {

  @Override
  public Optional<MrzData> parse(String mrzText) {
    if (mrzText == null || mrzText.isBlank()) return Optional.empty();

    String[] lines = mrzText.split("\n");
    if (lines.length < 2) return Optional.empty();

    String line1 = MrzTextUtils.padOrTrim(lines[0], 44);
    String line2 = MrzTextUtils.padOrTrim(lines[1], 44);

    try {
      String mrzCode = line1 + "\n" + line2;

      String documentTypeRaw = line1.substring(0, 2);
      String documentTypeName = DocumentTypeResolver.resolveName(documentTypeRaw);

      String issuingCountry = line1.substring(2, 5);
      String issuingCountryName = CountryCodeResolver.nameOf(issuingCountry);

      String rawNames = line1.substring(5);
      String[] nameParts = rawNames.split("<<", 2);
      String surname = nameParts.length > 0 ? nameParts[0].replace("<", " ").trim() : "";
      String givenNames = nameParts.length > 1 ? nameParts[1].replace("<", " ").trim() : "";
      String fullName = (givenNames + " " + surname).trim();
      if (fullName.isBlank()) fullName = (surname.isBlank() ? givenNames : surname);

      String documentNumber = line2.substring(0, 9).replace("<", "").trim();

      String nationality = line2.substring(10, 13);
      String nationalityName = CountryCodeResolver.nameOf(nationality);

      String birthDateRaw = line2.substring(13, 19);
      LocalDate birthDate = MrzDateParser.parse(birthDateRaw);

      String sex = String.valueOf(line2.charAt(20));
      String sexName = SexResolver.nameOf(sex);

      String expiryRaw = line2.substring(21, 27);
      LocalDate expiryDate = MrzDateParser.parse(expiryRaw);

      MrzData mrzData =
          new MrzData(
              line1,
              line2,
              mrzCode,
              documentTypeRaw,
              documentTypeName,
              documentNumber,
              issuingCountry,
              issuingCountryName,
              nationality,
              nationalityName,
              sex,
              sexName,
              surname,
              givenNames,
              fullName,
              birthDateRaw,
              birthDate,
              expiryRaw,
              expiryDate);

      return Optional.of(mrzData);
    } catch (Exception exception) {
      return Optional.empty();
    }
  }
}
