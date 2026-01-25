package com.github.andradenathan.documentprocessor.domain.document.validation.mrz;

import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationIssue;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationResult;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MrzTd3Validator implements DocumentValidator<MrzData> {
  @Override
  public boolean supports(MrzData data) {
    if (data == null) return false;

    return data.line1() != null
        && data.line2() != null
        && data.line1().length() == 44
        && data.line2().length() == 44;
  }

  @Override
  public DocumentValidationResult validate(MrzData mrzData) {
    List<DocumentValidationIssue> issues = new ArrayList<>();

    boolean isChecksumValid =
        MrzCheckDigits.isDocumentNumberValid(mrzData.line2())
            && MrzCheckDigits.isBirthDateValid(mrzData.line2())
            && MrzCheckDigits.isExpiryDateValid(mrzData.line2())
            && MrzCheckDigits.isCompositionValid(mrzData.line2());

    if (!isChecksumValid) {
      issues.add(
          new DocumentValidationIssue(
              "validation.mrz.checksum_invalid", "Checksum do MRZ inválido"));
    }

    if (mrzData.birthDateRaw() != null
        && !mrzData.birthDateRaw().isBlank()
        && mrzData.birthDate() == null) {
      issues.add(
          new DocumentValidationIssue(
              "validation.date.birth_invalid", "Data de nascimento inválida"));
    }

    if (mrzData.expiryDateRaw() != null
        && !mrzData.expiryDateRaw().isBlank()
        && mrzData.expiryDate() == null) {
      issues.add(
          new DocumentValidationIssue(
              "validation.date.expiry_invalid", "Data de expiração inválida"));
    }

    if (mrzData.isExpired(LocalDate.now())) {
      issues.add(new DocumentValidationIssue("validation.document.expired", "Documento expirado"));
    }

    if (mrzData.nationality() != null
        && !mrzData.nationality().isBlank()
        && !CountryCodeResolver.isKnown(mrzData.nationality())) {
      issues.add(
          new DocumentValidationIssue(
              "validation.nationality.unknown", "Nacionalidade desconhecida"));
    }

    if (mrzData.issuingCountry() != null
        && !mrzData.issuingCountry().isBlank()
        && !CountryCodeResolver.isKnown(mrzData.issuingCountry())) {
      issues.add(
          new DocumentValidationIssue("validation.country.unknown", "País emissor desconhecido"));
    }

    if (mrzData.documentTypeRaw() != null
        && !mrzData.documentTypeRaw().isBlank()
        && !DocumentTypeResolver.isValid(mrzData.documentTypeRaw())) {
      issues.add(
          new DocumentValidationIssue(
              "validation.document.type_unknown", "Tipo de documento desconhecido"));
    }

    if (mrzData.sex() != null && !mrzData.sex().isBlank() && !SexResolver.isValid(mrzData.sex())) {
      issues.add(new DocumentValidationIssue("validation.personal.sex_invalid", "Sexo inválido"));
    }

    if (mrzData.documentNumber() == null || mrzData.documentNumber().isBlank()) {
      issues.add(
          new DocumentValidationIssue(
              "validation.document.number_missing", "Número do documento ausente"));
    }

    boolean noSurname = mrzData.surname() == null || mrzData.surname().isBlank();
    boolean noGiven = mrzData.givenNames() == null || mrzData.givenNames().isBlank();
    if (noSurname && noGiven) {
      issues.add(new DocumentValidationIssue("validation.personal.name_missing", "Nome ausente"));
    }

    return issues.isEmpty() ? DocumentValidationResult.ok() : DocumentValidationResult.fail(issues);
  }
}
