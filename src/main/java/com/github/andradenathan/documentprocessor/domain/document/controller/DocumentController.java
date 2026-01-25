package com.github.andradenathan.documentprocessor.domain.document.controller;

import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import com.github.andradenathan.documentprocessor.domain.document.service.DocumentProcessorService;
import com.github.andradenathan.documentprocessor.infrastructure.http.BaseResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
  private final DocumentProcessorService documentProcessorService;

  public DocumentController(DocumentProcessorService documentProcessorService) {
    this.documentProcessorService = documentProcessorService;
  }

  @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BaseResponse> process(List<MultipartFile> documents) throws IOException {
    List<ProcessDocumentResponse> processed = documentProcessorService.process(documents);

    return ResponseEntity.ok(new BaseResponse(processed, "success"));
  }
}
