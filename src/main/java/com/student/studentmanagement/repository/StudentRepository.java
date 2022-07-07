package com.student.studentmanagement.repository;
import com.student.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data SQL repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Boolean existsByContactNumber(String contactNumber);
    Boolean existsByStudentId(String studentId);
    List<Student> findAllByIsActive(Boolean isActive);
    Page<Student> findAllByIsActiveAndNameContaining(Boolean isActive, String name, Pageable pageable);
}
