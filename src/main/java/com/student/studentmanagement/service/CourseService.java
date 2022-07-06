package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Course;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Course}.
 */
public interface CourseService {
    Course save(Course course);
    Optional<Course> partialUpdate(Course course);
    List<Course> findAll();
    Optional<Course> findOne(Long id);
    void delete(Long id);
}
