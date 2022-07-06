package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Departments}.
 */
public interface DepartmentsService {
    Departments save(Departments departments);
    Optional<Departments> partialUpdate(Departments departments);
    List<Departments> findAll();
    Optional<Departments> findOne(Long id);
    void delete(Long id);
}
