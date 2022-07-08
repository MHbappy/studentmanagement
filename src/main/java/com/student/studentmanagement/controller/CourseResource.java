package com.student.studentmanagement.controller;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseResource(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to save Course : {}", course);
        if (course.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new course cannot already have an ID");
        }
        Course result = courseService.save(course);
        return ResponseEntity
            .created(new URI("/api/courses/" + result.getId()))
            .body(result);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Course course
    ) throws URISyntaxException {
        log.debug("REST request to update Course : {}, {}", id, course);
        if (course.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, course.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        Course result = courseService.save(course);
        return ResponseEntity
            .ok()
            .body(result);
    }

//    @GetMapping("/courses")
//    public List<Course> getAllCourses() {
//        log.debug("REST request to get all Courses");
//        return courseService.findAll();
//    }


    @GetMapping("/searchByName")
    public Page<Course> getAllCourses(@RequestParam String name, Pageable pageable) {
        log.debug("REST request to get all Courses");
        if (name == null || name.isEmpty()){
            return courseService.findAllByIsActive(true, pageable);
        }
        return courseService.findAllByIsActiveAndNameContaining(true, name, pageable);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<Course> course = courseService.findOne(id);
        return ResponseEntity.ok(course.get());
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        Optional<Course> assignment = courseService.findOne(id);
        if (assignment.isPresent()){
            assignment.get().setIsActive(false);
            courseService.save(assignment.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
