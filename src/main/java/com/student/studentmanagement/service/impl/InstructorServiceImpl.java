package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.repository.InstructorRepository;
import com.student.studentmanagement.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Instructor}.
 */
@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);

    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
        log.debug("Request to save Instructor : {}", instructor);
        return instructorRepository.save(instructor);
    }

    @Override
    public Optional<Instructor> partialUpdate(Instructor instructor) {
        log.debug("Request to partially update Instructor : {}", instructor);

        return instructorRepository
            .findById(instructor.getId())
            .map(
                existingInstructor -> {
                    if (instructor.getName() != null) {
                        existingInstructor.setName(instructor.getName());
                    }
                    if (instructor.getTeacherId() != null) {
                        existingInstructor.setTeacherId(instructor.getTeacherId());
                    }
                    if (instructor.getAge() != null) {
                        existingInstructor.setAge(instructor.getAge());
                    }
                    if (instructor.getAddress() != null) {
                        existingInstructor.setAddress(instructor.getAddress());
                    }
                    if (instructor.getIsActive() != null) {
                        existingInstructor.setIsActive(instructor.getIsActive());
                    }

                    return existingInstructor;
                }
            )
            .map(instructorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instructor> findAll() {
        log.debug("Request to get all Instructors");
        return instructorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Instructor> findOne(Long id) {
        log.debug("Request to get Instructor : {}", id);
        return instructorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instructor : {}", id);
        instructorRepository.deleteById(id);
    }
}
