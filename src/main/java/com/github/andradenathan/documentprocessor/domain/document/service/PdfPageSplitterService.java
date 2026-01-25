package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.infra.http.MultipartFileConverter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class PdfPageSplitterService {
  public List<MultipartFile> splitToImages(MultipartFile pdf, int dpi) throws IOException {
    Objects.requireNonNull(pdf, "pdf must not be null");

    Path pdfPath = Files.createTempFile("uploaded-pdf-", ".pdf");
    File pdfFile = pdfPath.toFile();
    pdf.transferTo(pdfFile);

    List<MultipartFile> output = new ArrayList<>();

    try (PDDocument document = Loader.loadPDF(pdfFile)) {
      PDFRenderer renderer = new PDFRenderer(document);

      int pages = document.getNumberOfPages();
      for (int page = 0; page < pages; page++) {
        BufferedImage image = renderer.renderImageWithDPI(page, dpi, ImageType.RGB);

        File temporaryImage = File.createTempFile("pdf-page-" + (page + 1) + "-", ".png");

        ImageIO.write(image, "png", temporaryImage);

        output.add(new MultipartFileConverter(temporaryImage, "image/png"));
      }
    } finally {
      safeDelete(pdfFile);
    }

    return output;
  }

  private void safeDelete(File file) {
    try {
      if (file != null) Files.deleteIfExists(file.toPath());
    } catch (Exception exception) {
      log.debug("Failed to delete temporary file {}", file, exception);
    }
  }
}
