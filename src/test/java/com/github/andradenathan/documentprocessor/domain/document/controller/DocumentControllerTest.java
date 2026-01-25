package com.github.andradenathan.documentprocessor.domain.document.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.andradenathan.documentprocessor.domain.document.service.DocumentUploadService;
import com.github.andradenathan.documentprocessor.domain.document.service.MrzDocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockitoBean private DocumentUploadService documentUploadService;
  @MockitoBean private MrzDocumentService mrzDocumentService;

  @Test
  void shouldReturnAccepted() throws Exception {
    var file = new MockMultipartFile("documents", "doc.pdf", "application/pdf", "test".getBytes());

    mockMvc.perform(multipart("/api/documents/upload").file(file)).andExpect(status().isAccepted());

    verify(documentUploadService).process(any());
  }
}
