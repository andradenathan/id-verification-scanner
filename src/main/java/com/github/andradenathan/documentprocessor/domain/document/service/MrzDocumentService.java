package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.mrz.valueobjects.MrzData;
import com.github.andradenathan.documentprocessor.domain.document.repository.DocumentRepository;
import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import com.github.andradenathan.documentprocessor.domain.document.validation.DocumentValidationResult;
import com.github.andradenathan.documentprocessor.domain.document.validation.mrz.MrzTd3Validator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MrzDocumentService {
  private final DocumentRepository documentRepository;
  private final MrzTd3Validator documentValidatorService;

  public MrzDocumentService(
      DocumentRepository documentRepository, MrzTd3Validator documentValidatorService) {
    this.documentRepository = documentRepository;
    this.documentValidatorService = documentValidatorService;
  }

  public void saveAll(List<ProcessDocumentResponse> documents) {
    documentRepository.saveAll(documents.stream().map(ProcessDocumentResponse::document).toList());
  }

  public List<ProcessDocumentResponse> findAllDocumentsWithIssues() {
    return documentRepository.findAll().stream()
        .map(
            document -> {
              MrzData mrzData = MrzData.fromDocument(document);
              DocumentValidationResult validation = documentValidatorService.validate(mrzData);
              return ProcessDocumentResponse.of(document, validation.issues());
            })
        .toList();
  }
}
