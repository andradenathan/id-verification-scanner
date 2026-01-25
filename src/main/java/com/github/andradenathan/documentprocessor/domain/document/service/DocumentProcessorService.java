package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentProcessorService {
  private static final int COLORFUL_DPI = 300;
  private final MrzProcessorService mrzProcessorService;
  private final PdfPageSplitterService pdfPageSplitterService;

  public DocumentProcessorService(
      MrzProcessorService mrzProcessorService, PdfPageSplitterService pdfPageSplitterService) {
    this.mrzProcessorService = mrzProcessorService;
    this.pdfPageSplitterService = pdfPageSplitterService;
  }

  public List<ProcessDocumentResponse> process(List<MultipartFile> documents) throws IOException {
    if (documents == null || documents.isEmpty()) {
      return List.of();
    }

    List<MultipartFile> files = new ArrayList<>();

    for (MultipartFile document : documents) {
      if ("application/pdf".equals(document.getContentType())) {
        files.addAll(pdfPageSplitterService.splitToImages(document, COLORFUL_DPI));
      } else {
        files.add(document);
      }
    }

    return files.stream().map(mrzProcessorService::process).toList();
  }
}
