package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student save(Student student);
    Optional<Student> partialUpdate(Student student);
    List<Student> findAll();
    List<Student> findAllByIsActive(Boolean isActive);
    Page<Student> findAllByStudentName(Boolean isActive, String name, Pageable pageable);
    Optional<Student> findOne(Long id);
    void delete(Long id);
}
