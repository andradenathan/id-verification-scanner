package com.github.andradenathan.documentprocessor.domain.document.validation.mrz;

import java.util.Map;

public final class DocumentTypeResolver {

  private static final Map<String, String> TYPES =
      Map.ofEntries(
          Map.entry("P", "Passaporte"),
          Map.entry("P<", "Passaporte Comum"),
          Map.entry("PD", "Passaporte Diplomático"),
          Map.entry("PS", "Passaporte de Serviço"),
          Map.entry("PB", "Passaporte de Serviço"),
          Map.entry("V", "Visto"),
          Map.entry("VC", "Visto de Curta Duração"),
          Map.entry("VL", "Visto de Longa Duração"),
          Map.entry("VN", "Visto Nacional"),
          Map.entry("VB", "Visto de Negócios"),
          Map.entry("VT", "Visto de Trânsito"),
          Map.entry("VZ", "Visto Especial"),
          Map.entry("I", "Carteira de Identidade"),
          Map.entry("ID", "Documento de Identidade Nacional"),
          Map.entry("IP", "Passport Card"),
          Map.entry("IR", "Cartão de Residente Permanente"),
          Map.entry("IS", "Documento de Identidade de Marítimo"),
          Map.entry("A", "Documento de Viagem"),
          Map.entry("AR", "Cartão de Registro de Estrangeiro"),
          Map.entry("AC", "Certificado de Tripulante"),
          Map.entry("C", "Cartão de Identificação"),
          Map.entry("CA", "Cartão de Identificação / Residente"),
          Map.entry("R", "Documento de Viagem"),
          Map.entry("RT", "Documento de Viagem para Refugiados"),
          Map.entry("RE", "Permissão de Reentrada"));

  private DocumentTypeResolver() {}

  public static boolean isValid(String raw) {
    if (raw == null || raw.isBlank()) return false;
    String code = raw.trim().toUpperCase();
    if (TYPES.containsKey(code)) return true;
    return TYPES.containsKey(code.substring(0, 1));
  }

  public static String resolveName(String raw) {
    if (raw == null || raw.isBlank()) return "Desconhecido";
    String code = raw.trim().toUpperCase();
    if (TYPES.containsKey(code)) return TYPES.get(code);
    if (!code.isEmpty() && TYPES.containsKey(code.substring(0, 1)))
      return TYPES.get(code.substring(0, 1));
    return code;
  }
}
