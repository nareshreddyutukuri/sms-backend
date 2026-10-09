
package com.studentmanagement.controller;

import com.studentmanagement.model.Mark;
import com.studentmanagement.service.MarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    // ADMIN: Add marks for a student
    @PostMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mark> addMarks(
            @PathVariable Long studentId,
            @RequestBody Mark mark) {

        return ResponseEntity.ok(
                markService.addMarks(studentId, mark));
    }

    // ADMIN: View all marks
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Mark>> getAllMarks() {
        return ResponseEntity.ok(markService.getAllMarks());
    }

    // ADMIN: View a mark record
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mark> getMarkById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                markService.getMarkById(id));
    }

    // ADMIN: View any student's marks
    // STUDENT: View only their own marks
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or @studentAccess.isOwnStudent(#studentId, authentication.name)")
    public ResponseEntity<List<Mark>> getMarksByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                markService.getMarksByStudent(studentId));
    }

    // ADMIN: Search marks by subject
    @GetMapping("/subject/{subject}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Mark>> getMarksBySubject(
            @PathVariable String subject) {

        return ResponseEntity.ok(
                markService.getMarksBySubject(subject));
    }

    // ADMIN: Update marks
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mark> updateMarks(
            @PathVariable Long id,
            @RequestBody Mark mark) {

        return ResponseEntity.ok(
                markService.updateMarks(id, mark));
    }

    // ADMIN: Delete marks
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMarks(
            @PathVariable Long id) {

        markService.deleteMarks(id);

        return ResponseEntity.ok(
                "Marks deleted successfully");
    }
}