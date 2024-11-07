
package com.jizas.statcheck.controller;

import java.util.List;


import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.service.SubjectService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(method = RequestMethod.GET,path="/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

  
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SubjectEntity createSubject(@RequestBody SubjectEntity subject) {
        return subjectService.createSubject(subject);
    }


    
    @GetMapping("/getAll")
    public List<SubjectEntity> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    
    @GetMapping("/getById/{subjectId}")
    public SubjectEntity getSubjectById(@PathVariable int subjectId) {
        return subjectService.getSubjectById(subjectId);
    }

    @PutMapping("/update/{subjectId}")
    public SubjectEntity updateSubject(
            @PathVariable int subjectId,
            @RequestBody SubjectEntity updatedSubject) {
        return subjectService.updateSubject(subjectId, updatedSubject);
    }


    @DeleteMapping("/deleteById/{subjectId}")
    public void deleteSubjectById(@PathVariable int subjectId) {
        subjectService.deleteSubjectById(subjectId);
    }
}