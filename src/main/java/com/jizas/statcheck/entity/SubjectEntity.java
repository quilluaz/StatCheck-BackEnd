package com.jizas.statcheck.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subject")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectID;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "section")
    private String section;

    @Column(name = "instructor")
    private String instructor;

    @OneToMany(mappedBy = "subjectEntity", cascade = CascadeType.ALL)
    private List<TimeSlotEntity> timeslots;

    // Default constructor
    public SubjectEntity() {}

    // Constructor with fields
    public SubjectEntity(String subjectName, String section, String instructor) {
        this.subjectName = subjectName;
        this.section = section;
        this.instructor = instructor;
    }

    // Getters and Setters
    public Long getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<TimeSlotEntity> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<TimeSlotEntity> timeslots) {
        this.timeslots = timeslots;
    }
}
