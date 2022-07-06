package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Grade;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Grade}.
 */
public interface GradeService {
    Grade save(Grade grade);
    Optional<Grade> partialUpdate(Grade grade);
    List<Grade> findAll();
    Optional<Grade> findOne(Long id);
    void delete(Long id);
}
