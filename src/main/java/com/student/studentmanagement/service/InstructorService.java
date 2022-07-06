package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Instructor;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Instructor}.
 */
public interface InstructorService {
    Instructor save(Instructor instructor);
    Optional<Instructor> partialUpdate(Instructor instructor);
    List<Instructor> findAll();
    Optional<Instructor> findOne(Long id);
    void delete(Long id);
}
