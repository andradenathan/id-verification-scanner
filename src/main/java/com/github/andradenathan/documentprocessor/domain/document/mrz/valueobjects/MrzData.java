package com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import java.time.LocalDate;

public record MrzData(
    String line1,
    String line2,
    String mrz,
    String documentTypeRaw,
    String documentTypeName,
    String documentNumber,
    String issuingCountry,
    String issuingCountryName,
    String nationality,
    String nationalityName,
    String sex,
    String sexName,
    String surname,
    String givenNames,
    String fullName,
    String birthDateRaw,
    LocalDate birthDate,
    String expiryDateRaw,
    LocalDate expiryDate) {

  public static MrzData fromDocument(Document document) {
    return new MrzData(
        document.getMrz().substring(0, 44),
        document.getMrz().substring(44, 88),
        document.getMrz(),
        null,
        document.getType(),
        document.getNumber(),
        null,
        null,
        null,
        document.getNationality(),
        document.getSex(),
        null,
        null,
        document.getName(),
        null,
        document.getBirthDate() != null ? document.getBirthDate().toString() : null,
        document.getBirthDate(),
        document.getExpiryDate() != null ? document.getExpiryDate().toString() : null,
        document.getExpiryDate());
  }

  public static Document toDocument(MrzData mrz) {
    return new Document(
        mrz.mrz(),
        mrz.fullName(),
        mrz.documentNumber(),
        mrz.documentTypeName(),
        mrz.birthDate(),
        mrz.expiryDate(),
        mrz.nationalityName(),
        mrz.sex());
  }

  public boolean isExpired(LocalDate today) {
    return expiryDate != null && expiryDate.isBefore(today);
  }
}
