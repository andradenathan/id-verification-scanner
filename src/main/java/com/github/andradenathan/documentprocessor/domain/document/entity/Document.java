package com.github.andradenathan.documentprocessor.domain.document.entity;

import java.util.UUID;

public class Document {
  private final UUID id;
  private final String mrz;
  private final String name;
  private final String number;
  private final String expiryDate;
  private final String birthDate;
  private final String type;
  private final String nationality;

  public Document(
      String mrz,
      String name,
      String number,
      String type,
      String birthDate,
      String expiryDate,
      String nationality) {
    this.id = UUID.randomUUID();
    this.mrz = mrz;
    this.name = name;
    this.number = number;
    this.type = type;
    this.birthDate = birthDate;
    this.expiryDate = expiryDate;
    this.nationality = nationality;
  }

  public String getNationality() {
    return nationality;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public String getMrz() {
    return mrz;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getType() {
    return type;
  }

  public String getExpiryDate() {
    return expiryDate;
  }
}
