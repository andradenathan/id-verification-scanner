package com.github.andradenathan.documentprocessor.domain.document.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {
    public void process(List<MultipartFile> documents) {
        // TODO: Validate and process the uploaded documents
        // TODO: Publish it to notify workers for further processing.
    }
}
