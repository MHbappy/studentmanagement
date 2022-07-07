package com.student.studentmanagement.service.impl;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
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
                    if (student.getName() != null) {
                        existingStudent.setName(student.getName());
                    }
                    if (student.getStudentId() != null) {
                        existingStudent.setStudentId(student.getStudentId());
                    }
                    if (student.getAddress() != null) {
                        existingStudent.setAddress(student.getAddress());
                    }
                    if (student.getAge() != null) {
                        existingStudent.setAge(student.getAge());
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
    public Page<Student> findAllByStudentName(Boolean isActive, String name, Pageable pageable) {
        return studentRepository.findAllByIsActiveAndNameContaining(isActive, name, pageable);
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
