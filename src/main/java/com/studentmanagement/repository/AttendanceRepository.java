package com.studentmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagement.model.Attendance;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByStudentIdAndDate(
            Long studentId,
            LocalDate date
    );
}