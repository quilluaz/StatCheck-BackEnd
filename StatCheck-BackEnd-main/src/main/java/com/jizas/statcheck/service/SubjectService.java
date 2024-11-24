package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // Get all subjects
    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Get subject by ID
    public Optional<SubjectEntity> getSubjectById(Long subjectID) {
        return subjectRepository.findById(subjectID);
    }

    // Create a new subject
    public SubjectEntity createSubject(SubjectEntity subjectEntity) {
        return subjectRepository.save(subjectEntity);
    }

    // Update an existing subject
    public SubjectEntity updateSubject(Long subjectID, SubjectEntity subjectEntity) {
        Optional<SubjectEntity> existingSubject = subjectRepository.findById(subjectID);
        if (existingSubject.isPresent()) {
            SubjectEntity updatedSubject = existingSubject.get();
            updatedSubject.setSubjectName(subjectEntity.getSubjectName());
            updatedSubject.setSection(subjectEntity.getSection());
            updatedSubject.setInstructor(subjectEntity.getInstructor());
            return subjectRepository.save(updatedSubject);
        }
        return null;
    }

    // Delete a subject
    public void deleteSubject(Long subjectID) {
        subjectRepository.deleteById(subjectID);
    }
}
