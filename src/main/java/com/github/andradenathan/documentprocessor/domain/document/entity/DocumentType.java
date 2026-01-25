package com.github.andradenathan.documentprocessor.domain.document.entity;

public enum DocumentType {
  PASSPORT("P", "Passaporte"),
  PASSPORT_COMMON("P<", "Passaporte Comum"),
  PASSPORT_DIPLOMATIC("PD", "Passaporte Diplomático"),
  PASSPORT_SERVICE("PS", "Passaporte de Serviço"),

  VISA("V", "Visto"),
  VISA_SHORT_STAY("VC", "Visto de Curta Duração"),
  VISA_LONG_STAY("VL", "Visto de Longa Duração"),
  VISA_NATIONAL("VN", "Visto Nacional"),
  VISA_BUSINESS("VB", "Visto de Negócios"),
  VISA_TRANSIT("VT", "Visto de Trânsito"),
  VISA_SPECIAL("VZ", "Visto Especial"),

  IDENTITY_CARD("I", "Carteira de Identidade"),
  NATIONAL_ID("ID", "Documento de Identidade Nacional"),
  PASSPORT_CARD("IP", "Passport Card"),
  RESIDENCE_PERMIT("IR", "Cartão de Residente Permanente"),
  SEAFARER_ID("IS", "Documento de Identidade de Marítimo"),

  TRAVEL_DOCUMENT("A", "Documento de Viagem"),
  ALIEN_REGISTRATION("AR", "Cartão de Registro de Estrangeiro"),
  CREW_CERTIFICATE("AC", "Certificado de Tripulante"),

  REFUGEE_TRAVEL_DOCUMENT("RT", "Documento de Viagem para Refugiados"),
  REENTRY_PERMIT("RE", "Permissão de Reentrada"),

  UNKNOWN("?", "Desconhecido");

  private final String code;
  private final String description;

  DocumentType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String code() {
    return code;
  }

  public String description() {
    return description;
  }
}
