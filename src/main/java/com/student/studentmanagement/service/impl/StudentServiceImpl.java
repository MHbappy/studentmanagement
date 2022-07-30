package com.student.studentmanagement.service.impl;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;
import com.student.studentmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.ROLE_CLIENT);
//        String encodedPassword = passwordEncoder.encode(student.getPassword());

        Users users = new Users();
        users.setFirstname(student.getFirstName());
        users.setLastname(student.getLastName());
        users.setEmail(student.getEmail());
        users.setCreatedAt(LocalDateTime.now());
        users.setPassword(student.getPassword());
        users.setIsVerified(true);
        users.setAppUserRoles(userRoles);
        users.setUpdatedAt(LocalDateTime.now());
        userService.signup(users);
        student.setUseName(student.getEmail());
        return studentRepository.save(student);
    }

    @Override
    public Student updateSave(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(
                existingStudent -> {
                    if (student.getFirstName() != null) {
                        existingStudent.setFirstName(student.getFirstName());
                    }
                    if (student.getStudentId() != null) {
                        existingStudent.setStudentId(student.getStudentId());
                    }
                    if (student.getAddress() != null) {
                        existingStudent.setAddress(student.getAddress());
                    }
                    if (student.getDob() != null) {
                        existingStudent.setDob(student.getDob());
                    }
                    if (student.getIsActive() != null) {
                        existingStudent.setIsActive(student.getIsActive());
                    }
                    return existingStudent;
                }
            )
            .map(studentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllByIsActive(Boolean isActive) {
        return studentRepository.findAllByIsActive(isActive);
    }

    @Override
    public List<Student> getAllStudentsByCourse(Long courseId) {
        return studentRepository.getAllStudentsByCourse(courseId);
    }

    @Override
    public Page<Student> findAllByStudentName(Boolean isActive, String name, Pageable pageable) {
        name = "%" + name + "%";
        return studentRepository.findAllByIsActiveAndFirstNameLike(isActive, name, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
