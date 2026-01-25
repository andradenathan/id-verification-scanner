package com.github.andradenathan.documentprocessor.infrastructure.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface DocumentStorage {
  void save(UUID documentId, String filename, InputStream content);

  Optional<File> load(UUID documentId);

  void delete(UUID documentId) throws IOException;
}
