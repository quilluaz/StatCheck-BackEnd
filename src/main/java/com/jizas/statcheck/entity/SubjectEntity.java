package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "subject")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @NotBlank(message = "Subject name must not be empty")
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "section")
    private String section;

    @Column(name = "instructor")
    private String instructor;

    @OneToMany(mappedBy = "subjectEntity", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"subjectEntity"})
    private List<ScheduleEntity> schedules = new ArrayList<>();

    public SubjectEntity() {}

    public SubjectEntity(String subjectName, String section, String instructor) {
        this.subjectName = subjectName;
        this.section = section;
        this.instructor = instructor;
    }

    // Getters and Setters
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public List<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }
}
