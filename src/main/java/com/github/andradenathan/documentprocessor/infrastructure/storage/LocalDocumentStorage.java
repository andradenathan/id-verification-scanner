package com.github.andradenathan.documentprocessor.infrastructure.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocalDocumentStorage implements DocumentStorage {
  private final Path rootDirectory;

  public LocalDocumentStorage(
      @Value("${application.document.storage.local.path:./data/documents}") String rootPath) {
    this.rootDirectory = Paths.get(rootPath).toAbsolutePath();

    try {
      Files.createDirectories(rootDirectory);
    } catch (IOException exception) {
      throw new IllegalStateException(
          "Failed to create storage directory: " + rootDirectory, exception);
    }
  }

  @Override
  public void save(UUID documentId, String filename, InputStream content) {
    Path dir = rootDirectory.resolve(documentId.toString());
    Path file = dir.resolve(filename);

    try {
      Files.createDirectories(dir);
      Files.copy(content, file, StandardCopyOption.REPLACE_EXISTING);
      log.debug("Saved document {} at {}", documentId, file);
    } catch (IOException exception) {
      throw new RuntimeException("Failed to save document " + documentId, exception);
    }
  }

  @Override
  public Optional<File> load(UUID documentId) {
    Path dir = rootDirectory.resolve(documentId.toString());
    if (!Files.exists(dir) || !Files.isDirectory(dir)) return Optional.empty();

    try (Stream<Path> paths = Files.list(dir)) {
      return paths.filter(Files::isRegularFile).findFirst().map(Path::toFile);
    } catch (IOException exception) {
      log.warn("Failed to load document {}", documentId, exception);
      return Optional.empty();
    }
  }

  @Override
  public void delete(UUID documentId) {
    Path dir = rootDirectory.resolve(documentId.toString());
    if (!Files.exists(dir)) return;

    try (Stream<Path> paths = Files.walk(dir)) {
      paths
          .sorted(Comparator.reverseOrder())
          .forEach(
              path -> {
                try {
                  Files.deleteIfExists(path);
                } catch (IOException exception) {
                  log.warn("Failed to delete {}", path, exception);
                }
              });

    } catch (IOException exception) {
      log.warn("Failed to delete document {}", documentId, exception);
    }
  }
}
