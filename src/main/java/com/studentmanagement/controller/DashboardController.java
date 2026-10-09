
package com.studentmanagement.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagement.repository.AttendanceRepository;
import com.studentmanagement.repository.MarkRepository;
import com.studentmanagement.repository.StudentRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class DashboardController {

    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final MarkRepository markRepository;

    public DashboardController(
            StudentRepository studentRepository,
            AttendanceRepository attendanceRepository,
            MarkRepository markRepository) {

        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
        this.markRepository = markRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {

        Map<String, Long> stats = new LinkedHashMap<>();

        stats.put("totalStudents",
                studentRepository.count());

        stats.put("totalAttendanceRecords",
                attendanceRepository.count());

        stats.put("totalMarksRecords",
                markRepository.count());

        return ResponseEntity.ok(stats);
    }
}