package com.student.studentmanagement.controller;
import com.student.studentmanagement.dto.StudentDTO;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentResource(StudentService studentService, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Student student = modelMapper.map(studentDTO, Student.class);
        LocalDate dob = LocalDate.parse(studentDTO.getDob());
        student.setDob(dob);

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
        StudentDTO studentDTOResult = modelMapper.map(result, StudentDTO.class);
        return ResponseEntity
            .created(new URI("/api/students/" + studentDTOResult.getId()))
            .body(studentDTOResult);
    }


    @PostMapping("/studentsRegistration")
    public ResponseEntity<StudentDTO> registrationStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Student student = modelMapper.map(studentDTO, Student.class);
        LocalDate dob = LocalDate.parse(studentDTO.getDob());
        student.setDob(dob);

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
        StudentDTO studentDTOResult = modelMapper.map(result, StudentDTO.class);
        return ResponseEntity
                .created(new URI("/api/students/" + studentDTOResult.getId()))
                .body(studentDTOResult);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentDTO> updateStudent(
        @PathVariable(value = "id", required = false) final Long id,
         @RequestBody StudentDTO studentDTO
    ) {
        Student student = modelMapper.map(studentDTO, Student.class);

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
        LocalDate dob;
        if (studentDTO.getDob() != null){
            dob = LocalDate.parse(studentDTO.getDob());
            student.setDob(dob);
        }

        Student result = studentService.updateSave(student);
        StudentDTO studentDTOResult = modelMapper.map(result, StudentDTO.class);
        return ResponseEntity
            .ok()
            .body(studentDTOResult);
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

    @GetMapping("/studentByCourseId")
    public List<Student> getAllStudentsByCourse(@RequestParam Long courseId) {
        return studentRepository.getAllStudentsByCourse(courseId);
    }
}
