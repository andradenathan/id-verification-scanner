package com.github.andradenathan.documentprocessor.infrastructure.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileConverter implements MultipartFile {
  private final File file;
  private final String contentType;

  public MultipartFileConverter(File file, String contentType) {
    this.file = file;
    this.contentType = contentType;
  }

  @Override
  public String getName() {
    return file.getName();
  }

  @Override
  public String getOriginalFilename() {
    return file.getName();
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public boolean isEmpty() {
    return file.length() == 0;
  }

  @Override
  public long getSize() {
    return file.length();
  }

  @Override
  public byte[] getBytes() throws IOException {
    return Files.readAllBytes(file.toPath());
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(getBytes());
  }

  @Override
  public void transferTo(File dest) throws IOException {
    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }
}
