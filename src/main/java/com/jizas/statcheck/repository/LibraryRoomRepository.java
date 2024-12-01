package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRoomRepository extends JpaRepository<LibraryRoomEntity, Long> {
    List<LibraryRoomEntity> findByLibraryLibraryID(Long libraryId);
}
