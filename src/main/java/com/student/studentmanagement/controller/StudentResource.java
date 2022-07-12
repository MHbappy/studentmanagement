package com.student.studentmanagement.controller;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentResource(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to save Student : {}", student);
        if (student.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new student cannot already have an ID.");
        }

        if (studentRepository.existsByStudentId(student.getStudentId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new student cannot already have an ID.");
        }

        if (studentRepository.existsByContactNumber(student.getContactNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new student cannot already have an contact number.");
        }

        Student result = studentService.save(student);
        return ResponseEntity
            .created(new URI("/api/students/" + result.getId()))
            .body(result);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Student> updateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Student student
    ) {
        log.debug("REST request to update Student : {}, {}", id, student);
        if (student.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new student cannot already have an ID.");
        }

        if (!Objects.equals(id, student.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID.");
        }

        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found.");
        }

        Student result = studentService.save(student);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/searchByStudentName")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<Student> getAllStudents(@RequestParam String name, Pageable pageable) {
        log.debug("REST request to get all Students");
        return studentService.findAllByStudentName(true, name, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<Student> student = studentService.findOne(id);
        return ResponseEntity.ok(student.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        Optional<Student> student = studentService.findOne(id);
        if (student.isPresent()){
            student.get().setIsActive(false);
            studentService.save(student.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
