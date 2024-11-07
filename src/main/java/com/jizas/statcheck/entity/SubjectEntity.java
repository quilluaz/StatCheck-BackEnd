package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "subjectId")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subjectId;

    private String subjectName;
    private String section;
    private String instructor;
    private int classCapacity;


    // One-to-Many relationship with TimeSlotEntity
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<TimeSlotEntity> timeSlots = new ArrayList<>();


    public SubjectEntity() {}


    public SubjectEntity(String subjectName, String section, String instructor, int classCapacity) {
        this.subjectName = subjectName;
        this.section = section;
        this.instructor = instructor;
        this.classCapacity = classCapacity;
    }

 
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSection(){
        return section;
    }

    public void setSection(String section){
        this.section = section;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    public List<TimeSlotEntity> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotEntity> timeSlots) {
        this.timeSlots = timeSlots;
    }
}

