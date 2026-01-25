package com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects;

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
  public boolean isExpired(LocalDate today) {
    return expiryDate != null && expiryDate.isBefore(today);
  }
}
