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

    // Find subjects by name (partial match)
    public List<SubjectEntity> findSubjectsByName(String subjectName) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName);
    }

    // Find subjects by section
    public List<SubjectEntity> findSubjectsBySection(String section) {
        return subjectRepository.findBySection(section);
    }

    // Find subjects by instructor (partial match)
    public List<SubjectEntity> findSubjectsByInstructor(String instructor) {
        return subjectRepository.findByInstructorContainingIgnoreCase(instructor);
    }

    // Create a new subject with validation
    public SubjectEntity createSubject(SubjectEntity subjectEntity) {
        // Check if subject with same name and section already exists
        if (subjectRepository.existsBySubjectNameAndSection(
                subjectEntity.getSubjectName(), 
                subjectEntity.getSection())) {
            throw new IllegalArgumentException("Subject with this name and section already exists");
        }
        return subjectRepository.save(subjectEntity);
    }

    // Update an existing subject
    public SubjectEntity updateSubject(Long subjectID, SubjectEntity subjectEntity) {
        Optional<SubjectEntity> existingSubject = subjectRepository.findById(subjectID);
        if (existingSubject.isPresent()) {
            SubjectEntity updatedSubject = existingSubject.get();
            
            // Check if update would create a duplicate (excluding current subject)
            if (!updatedSubject.getSubjectName().equals(subjectEntity.getSubjectName()) ||
                !updatedSubject.getSection().equals(subjectEntity.getSection())) {
                if (subjectRepository.existsBySubjectNameAndSection(
                        subjectEntity.getSubjectName(), 
                        subjectEntity.getSection())) {
                    throw new IllegalArgumentException("Cannot update: Subject with this name and section already exists");
                }
            }
            
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
