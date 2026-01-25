package com.github.andradenathan.documentprocessor.domain.document.repository;

import com.github.andradenathan.documentprocessor.domain.document.entity.Document;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, UUID> {}
