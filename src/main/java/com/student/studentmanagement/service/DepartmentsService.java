package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Departments}.
 */
public interface DepartmentsService {
    Departments save(Departments departments);
    Optional<Departments> partialUpdate(Departments departments);
    List<Departments> findAll();
    List<Departments> findAllByIsActive(Boolean isActive);
    Optional<Departments> findOne(Long id);
    void delete(Long id);
}
