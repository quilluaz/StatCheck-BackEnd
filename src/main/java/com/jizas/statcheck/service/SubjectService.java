
package com.jizas.statcheck.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.repository.SubjectRepository;


@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectEntity createSubject(SubjectEntity subject) {
        return subjectRepository.save(subject);
    }

    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public SubjectEntity getSubjectById(int subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NoSuchElementException("Subject not found with ID: " + subjectId));
    }

    public SubjectEntity updateSubject(int subjectId, SubjectEntity updatedSubject) {
        SubjectEntity existingSubject = getSubjectById(subjectId);
        existingSubject.setSubjectName(updatedSubject.getSubjectName());
        existingSubject.setSection(updatedSubject.getSection());
        existingSubject.setInstructor(updatedSubject.getInstructor());
        existingSubject.setClassCapacity(updatedSubject.getClassCapacity());
        existingSubject.setTimeSlots(updatedSubject.getTimeSlots()); // Update related time slots if necessary
        return subjectRepository.save(existingSubject);
    }

    public void deleteSubjectById(int subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}