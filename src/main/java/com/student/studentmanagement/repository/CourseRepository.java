package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsActive(Boolean isActive);
    Page<Course> findAllByIsActiveAndNameContaining(Boolean isActive, String name, Pageable pageable);
}
