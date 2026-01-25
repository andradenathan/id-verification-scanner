package com.github.andradenathan.documentprocessor.domain.document.validation;

import com.github.andradenathan.documentprocessor.domain.document.entity.FileType;
import java.io.File;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileTypeResolver {
  public static FileType resolve(File file) {
    String name = file.getName().toLowerCase();

    if (name.endsWith(".pdf")) return FileType.PDF;
    if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"))
      return FileType.IMAGE;

    try {
      String contentType = Files.probeContentType(file.toPath());
      if (contentType == null) return FileType.UNKNOWN;

      if (contentType.equals("application/pdf")) return FileType.PDF;
      if (contentType.startsWith("image/")) return FileType.IMAGE;
    } catch (Exception exception) {
      log.warn("Could not determine file type for file={}", file.getAbsolutePath());
    }

    return FileType.UNKNOWN;
  }
}
