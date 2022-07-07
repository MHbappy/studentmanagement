package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Assignment;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Assignment}.
 */
public interface AssignmentService {
    Assignment save(Assignment assignment);
    Optional<Assignment> partialUpdate(Assignment assignment);
    List<Assignment> findAll();
    List<Assignment> findAllByIsActive(Boolean isActive);
    Optional<Assignment> findOne(Long id);
    void delete(Long id);
}
