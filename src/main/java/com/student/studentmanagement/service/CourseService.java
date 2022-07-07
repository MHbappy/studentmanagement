package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Course}.
 */
public interface CourseService {
    Course save(Course course);
    Optional<Course> partialUpdate(Course course);
    List<Course> findAll();
    List<Course> findAllByIsActive(Boolean isActive);
    Page<Course> findAllByIsActiveAndNameContaining(Boolean isActive, String name, Pageable pageable);
    Optional<Course> findOne(Long id);
    void delete(Long id);
}
