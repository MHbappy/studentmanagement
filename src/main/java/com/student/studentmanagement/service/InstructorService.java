package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Instructor}.
 */
public interface InstructorService {
    Instructor save(Instructor instructor);
    Optional<Instructor> partialUpdate(Instructor instructor);
    List<Instructor> findAll();
    List<Instructor> findAllByIsActive(Boolean isActive);
    Optional<Instructor> findOne(Long id);
    void delete(Long id);
}
