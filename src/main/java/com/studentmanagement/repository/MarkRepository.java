package com.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagement.model.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByStudentId(Long studentId);

    List<Mark> findBySubjectIgnoreCase(String subject);
}