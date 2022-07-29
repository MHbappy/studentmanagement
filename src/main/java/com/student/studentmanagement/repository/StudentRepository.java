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
    Student findByUseName(String userName);
    @Query(value = "select distinct s.* from student s, departments d, course c WHERE s.departments_id = d.id AND d.id = c.departments_id AND c.id = ?1", nativeQuery = true)
    List<Student> getAllStudentsByCourse(Long courseId);
    @Query(value = "select * from student WHERE first_name like ?2 AND is_active = ?1", nativeQuery = true)
    Page<Student> findAllByIsActiveAndFirstNameLike(Boolean isActive, String name, Pageable pageable);
}
