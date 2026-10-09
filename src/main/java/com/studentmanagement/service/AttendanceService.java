package com.studentmanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studentmanagement.model.Attendance;
import com.studentmanagement.model.AttendanceStatus;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.AttendanceRepository;
import com.studentmanagement.repository.StudentRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    // CREATE
    public Attendance markAttendance(
            Long studentId,
            Attendance attendance) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        attendance.setStudent(student);

        return attendanceRepository.save(attendance);
    }

    // READ ALL
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // READ BY STUDENT
    public List<Attendance> getAttendanceByStudent(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found");
        }

        return attendanceRepository.findByStudentId(studentId);
    }

    // READ ONE
    public Attendance getAttendanceById(Long id) {

        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attendance not found"));
    }

    // UPDATE
    public Attendance updateAttendance(
            Long id,
            Attendance attendanceDetails) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Attendance not found"));

        attendance.setDate(attendanceDetails.getDate());
        attendance.setStatus(attendanceDetails.getStatus());

        return attendanceRepository.save(attendance);
    }

    // DELETE
    public void deleteAttendance(Long id) {

        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found");
        }

        attendanceRepository.deleteById(id);
    }

    // SEARCH BY DATE
    public List<Attendance> getAttendanceByDate(
            LocalDate date) {

        return attendanceRepository.findByDate(date);
    }

    // ATTENDANCE PERCENTAGE
    public double getAttendancePercentage(Long studentId) {

        List<Attendance> records =
                attendanceRepository.findByStudentId(studentId);

        if (records.isEmpty()) {
            return 0.0;
        }

        long presentCount = records.stream()
                .filter(a ->
                        a.getStatus() == AttendanceStatus.PRESENT ||
                        a.getStatus() == AttendanceStatus.LATE)
                .count();

        return (presentCount * 100.0) / records.size();
    }
}