package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() {
        List<SubjectEntity> subjects = subjectService.getAllSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEntity> getSubjectById(@PathVariable("id") Long subjectId) {
        Optional<SubjectEntity> subject = subjectService.getSubjectById(subjectId);
        return subject.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SubjectEntity> createSubject(@Valid @RequestBody SubjectEntity subjectEntity) {
        try {
            SubjectEntity savedSubject = subjectService.createSubject(subjectEntity);
            return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectEntity> updateSubject(
            @PathVariable("id") Long subjectId,
            @Valid @RequestBody SubjectEntity subjectEntity) {
        try {
            SubjectEntity updatedSubject = subjectService.updateSubject(subjectId, subjectEntity);
            if (updatedSubject != null) {
                return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
