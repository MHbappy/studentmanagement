package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Instructor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findAllByIsActive(Boolean isActive);
    Boolean existsByTeacherId(String instructorId);
}
