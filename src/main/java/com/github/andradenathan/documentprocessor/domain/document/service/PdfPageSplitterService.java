package com.github.andradenathan.documentprocessor.domain.document.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PdfPageSplitterService {
  public List<File> splitToImages(File pdf, int dpi) throws IOException {
    Objects.requireNonNull(pdf, "pdf must not be null");

    List<File> files = new ArrayList<>();

    try (PDDocument document = Loader.loadPDF(pdf)) {
      PDFRenderer renderer = new PDFRenderer(document);
      int pages = document.getNumberOfPages();

      for (int page = 0; page < pages; page++) {
        BufferedImage image = renderer.renderImageWithDPI(page, dpi, ImageType.RGB);

        File imageFile = File.createTempFile("pdf-page-" + (page + 1) + "-", ".png");
        ImageIO.write(image, "png", imageFile);
        files.add(imageFile);
      }
    }
    return files;
  }
}
