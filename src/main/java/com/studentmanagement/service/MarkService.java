package com.studentmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studentmanagement.model.Mark;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.MarkRepository;
import com.studentmanagement.repository.StudentRepository;

@Service
public class MarkService {

    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;

    public MarkService(
            MarkRepository markRepository,
            StudentRepository studentRepository) {

        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
    }

    // CREATE
    public Mark addMarks(Long studentId, Mark mark) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        mark.setStudent(student);

        calculateResult(mark);

        return markRepository.save(mark);
    }

    // READ ALL
    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    // READ ONE
    public Mark getMarkById(Long id) {

        return markRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mark record not found"));
    }

    // READ BY STUDENT
    public List<Mark> getMarksByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        return markRepository.findByStudentId(studentId);
    }

    // UPDATE
    public Mark updateMarks(Long id, Mark markDetails) {

        Mark mark = markRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mark record not found"));

        mark.setSubject(markDetails.getSubject());
        mark.setInternalMarks(markDetails.getInternalMarks());
        mark.setExternalMarks(markDetails.getExternalMarks());

        calculateResult(mark);

        return markRepository.save(mark);
    }

    // DELETE
    public void deleteMarks(Long id) {

        if (!markRepository.existsById(id)) {
            throw new RuntimeException("Mark record not found");
        }

        markRepository.deleteById(id);
    }

    // SEARCH BY SUBJECT
    public List<Mark> getMarksBySubject(String subject) {

        return markRepository.findBySubjectIgnoreCase(subject);
    }

    // CALCULATE TOTAL, PERCENTAGE AND GRADE
    private void calculateResult(Mark mark) {

        int internal = mark.getInternalMarks();
        int external = mark.getExternalMarks();

        int total = internal + external;

        double percentage = (total / 100.0) * 100;

        String grade;

        if (percentage >= 90) {
            grade = "A+";
        } else if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else if (percentage >= 40) {
            grade = "E";
        } else {
            grade = "F";
        }

        mark.setTotalMarks(total);
        mark.setPercentage(percentage);
        mark.setGrade(grade);
    }
}