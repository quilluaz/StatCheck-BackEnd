package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findBySubjectNameContainingIgnoreCase(String subjectName);
    List<SubjectEntity> findBySection(String section);
    List<SubjectEntity> findByInstructorContainingIgnoreCase(String instructor);
    boolean existsBySubjectNameAndSection(String subjectName, String section);
}
