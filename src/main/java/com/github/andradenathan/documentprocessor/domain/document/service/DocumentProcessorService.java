package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.domain.document.entity.FileType;
import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import com.github.andradenathan.documentprocessor.domain.document.validation.FileTypeResolver;
import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DocumentProcessorService {
  private static final int DPI = 300;

  private final PdfPageSplitterService pdfPageSplitterService;
  private final MrzProcessorService mrzProcessorService;

  public DocumentProcessorService(
      PdfPageSplitterService pdfPageSplitterService, MrzProcessorService mrzProcessorService) {
    this.pdfPageSplitterService = pdfPageSplitterService;
    this.mrzProcessorService = mrzProcessorService;
  }

  public List<ProcessDocumentResponse> process(File file) {
    FileType type = FileTypeResolver.resolve(file);

    return switch (type) {
      case PDF -> {
        try {
          List<File> pages = pdfPageSplitterService.splitToImages(file, DPI);
          yield pages.stream().map(mrzProcessorService::process).toList();
        } catch (Exception exception) {
          yield List.of(ProcessDocumentResponse.empty());
        }
      }
      case IMAGE -> List.of(mrzProcessorService.process(file));
      case UNKNOWN -> List.of(ProcessDocumentResponse.empty());
    };
  }
}
