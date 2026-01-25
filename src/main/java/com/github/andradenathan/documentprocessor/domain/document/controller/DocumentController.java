package com.github.andradenathan.documentprocessor.domain.document.controller;

import com.github.andradenathan.documentprocessor.domain.document.service.DocumentUploadService;
import com.github.andradenathan.documentprocessor.domain.document.service.MrzDocumentService;
import com.github.andradenathan.documentprocessor.infrastructure.http.BaseResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
  private final DocumentUploadService documentUploadService;
  private final MrzDocumentService mrzDocumentService;

  public DocumentController(
      DocumentUploadService documentUploadService, MrzDocumentService mrzDocumentService) {
    this.documentUploadService = documentUploadService;
    this.mrzDocumentService = mrzDocumentService;
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> upload(List<MultipartFile> documents) {
    documentUploadService.process(documents);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<BaseResponse> getDocuments() {
    return ResponseEntity.ok(BaseResponse.success(mrzDocumentService.findAllDocumentsWithIssues()));
  }
}
