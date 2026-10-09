
package com.studentmanagement.service;

import org.springframework.stereotype.Service;

import com.studentmanagement.model.User;
import com.studentmanagement.repository.UserRepository;

@Service("studentAccess")
public class StudentAccessService {

    private final UserRepository userRepository;

    public StudentAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwnStudent(Long studentId, String username) {
        if (studentId == null || username == null) {
            return false;
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null || user.getStudent() == null) {
            return false;
        }

        Long linkedStudentId = user.getStudent().getId();

        return linkedStudentId != null && linkedStudentId.equals(studentId);
    }
}