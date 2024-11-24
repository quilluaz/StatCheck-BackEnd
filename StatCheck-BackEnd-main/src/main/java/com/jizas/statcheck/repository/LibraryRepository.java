package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
