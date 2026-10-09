
package com.studentmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ADMIN: View all students
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ADMIN: View any student
    // STUDENT: View only their own student record
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @studentAccess.isOwnStudent(#id, authentication.name)")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // ADMIN: Add a student
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> addStudent(
            @RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // ADMIN: Update a student
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return ResponseEntity.ok(
                studentService.updateStudent(id, student));
    }

    // ADMIN: Delete a student
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // ADMIN: Search students by name
    @GetMapping("/search/name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Student>> searchByName(
            @RequestParam String name) {
        return ResponseEntity.ok(
                studentService.searchByName(name));
    }

    // ADMIN: Search students by course
    @GetMapping("/search/course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Student>> searchByCourse(
            @RequestParam String course) {
        return ResponseEntity.ok(
                studentService.searchByCourse(course));
    }
}