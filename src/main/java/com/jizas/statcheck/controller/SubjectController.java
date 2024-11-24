package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<SubjectEntity> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{subjectID}")
    public Optional<SubjectEntity> getSubjectById(@PathVariable Long subjectID) {
        return subjectService.getSubjectById(subjectID);
    }

    @PostMapping
    public SubjectEntity createSubject(@RequestBody SubjectEntity subjectEntity) {
        return subjectService.createSubject(subjectEntity);
    }

    @PutMapping("/{subjectID}")
    public SubjectEntity updateSubject(@PathVariable Long subjectID, @RequestBody SubjectEntity subjectEntity) {
        return subjectService.updateSubject(subjectID, subjectEntity);
    }

    @DeleteMapping("/{subjectID}")
    public void deleteSubject(@PathVariable Long subjectID) {
        subjectService.deleteSubject(subjectID);
    }
}
