package com.studentmanagement.service;

import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // READ ALL
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // READ ONE
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // UPDATE
    public Student updateStudent(Long id, Student studentDetails) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(studentDetails.getName());
        student.setRollNumber(studentDetails.getRollNumber());
        student.setCourse(studentDetails.getCourse());
        student.setYear(studentDetails.getYear());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());

        return studentRepository.save(student);
    }

    // DELETE
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }

        studentRepository.deleteById(id);
    }

    // SEARCH BY NAME
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // SEARCH BY COURSE
    public List<Student> searchByCourse(String course) {
        return studentRepository.findByCourseIgnoreCase(course);
    }
}