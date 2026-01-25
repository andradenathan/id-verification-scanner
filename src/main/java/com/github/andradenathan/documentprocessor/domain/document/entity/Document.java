package com.github.andradenathan.documentprocessor.domain.document.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;

@Getter
@Entity(name = "documents")
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String mrz;
  private String name;
  private String number;

  @Column(name = "expiry_date")
  private LocalDate expiryDate;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  private String type;
  private String nationality;
  private String sex;

  public Document(
      String mrz,
      String name,
      String number,
      String type,
      LocalDate birthDate,
      LocalDate expiryDate,
      String nationality,
      String sex) {
    this.mrz = mrz;
    this.name = name;
    this.number = number;
    this.type = type;
    this.birthDate = birthDate;
    this.expiryDate = expiryDate;
    this.nationality = nationality;
    this.sex = sex;
  }

  @Deprecated
  public Document() {}
}
