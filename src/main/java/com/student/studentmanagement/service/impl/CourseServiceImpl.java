package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Course}.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(Course course) {
        log.debug("Request to save Course : {}", course);
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> partialUpdate(Course course) {
        log.debug("Request to partially update Course : {}", course);

        return courseRepository
            .findById(course.getId())
            .map(
                existingCourse -> {
                    if (course.getName() != null) {
                        existingCourse.setName(course.getName());
                    }
                    if (course.getCode() != null) {
                        existingCourse.setCode(course.getCode());
                    }
                    if (course.getIsActive() != null) {
                        existingCourse.setIsActive(course.getIsActive());
                    }

                    return existingCourse;
                }
            )
            .map(courseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllByIsActive(Boolean isActive) {
        return courseRepository.findAllByIsActive(isActive);
    }

    @Override
    public Page<Course> findAllByIsActiveAndNameContaining(Boolean isActive, String name, Pageable pageable) {
        return courseRepository.findAllByIsActiveAndNameContaining(isActive, name, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
