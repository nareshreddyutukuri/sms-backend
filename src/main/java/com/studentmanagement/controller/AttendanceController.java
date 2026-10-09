
package com.studentmanagement.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.model.Attendance;
import com.studentmanagement.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ADMIN: Mark attendance for a student
    @PostMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable Long studentId,
            @RequestBody Attendance attendance) {
        return ResponseEntity.ok(
                attendanceService.markAttendance(studentId, attendance));
    }

    // ADMIN: View all attendance records
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    // ADMIN: View any attendance record
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Attendance> getAttendanceById(
            @PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    // ADMIN: View attendance for any student
    // STUDENT: View only their own attendance
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or @studentAccess.isOwnStudent(#studentId, authentication.name)")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudent(studentId));
    }

    // ADMIN: Search attendance by date
    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(
                attendanceService.getAttendanceByDate(date));
    }

    // ADMIN: View any student's attendance percentage
    // STUDENT: View only their own percentage
    @GetMapping("/student/{studentId}/percentage")
    @PreAuthorize("hasRole('ADMIN') or @studentAccess.isOwnStudent(#studentId, authentication.name)")
    public ResponseEntity<Double> getAttendancePercentage(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(
                attendanceService.getAttendancePercentage(studentId));
    }

    // ADMIN: Update attendance
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {
        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, attendance));
    }

    // ADMIN: Delete attendance
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAttendance(
            @PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok("Attendance deleted successfully");
    }
}