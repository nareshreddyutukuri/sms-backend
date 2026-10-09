package com.studentmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotBlank(message = "Subject is required")
    @Column(nullable = false)
    private String subject;

    @NotNull(message = "Internal marks are required")
    @Min(value = 0, message = "Internal marks cannot be negative")
    @Max(value = 40, message = "Internal marks cannot exceed 40")
    private Integer internalMarks;

    @NotNull(message = "External marks are required")
    @Min(value = 0, message = "External marks cannot be negative")
    @Max(value = 60, message = "External marks cannot exceed 60")
    private Integer externalMarks;

    private Integer totalMarks;

    private Double percentage;

    private String grade;

    public Mark() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(Integer externalMarks) {
        this.externalMarks = externalMarks;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}