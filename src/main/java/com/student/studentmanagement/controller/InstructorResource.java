package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.InstructorDTO;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.repository.InstructorRepository;
import com.student.studentmanagement.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class InstructorResource {

    private final Logger log = LoggerFactory.getLogger(InstructorResource.class);
    private final InstructorService instructorService;
    private final InstructorRepository instructorRepository;
    private final ModelMapper modelMapper;

    public InstructorResource(InstructorService instructorService, InstructorRepository instructorRepository, ModelMapper modelMapper) {
        this.instructorService = instructorService;
        this.instructorRepository = instructorRepository;
        this.modelMapper = modelMapper;
    }
    
    @PostMapping("/instructors")
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorDTO instructorDTO) throws URISyntaxException {

        Instructor instructor = modelMapper.map(instructorDTO, Instructor.class);
        log.debug("REST request to save Instructor : {}", instructor);
        if (instructor.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Instructor not found.");
        }
        if (instructorRepository.existsByTeacherId(instructor.getTeacherId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate teacther ID.");
        }
        Instructor result = instructorService.save(instructor);
        InstructorDTO instructorDTOResult = modelMapper.map(result, InstructorDTO.class);
        return ResponseEntity
            .created(new URI("/api/instructors/" + instructorDTOResult.getId()))
            .body(instructorDTOResult);
    }
    
    @PutMapping("/instructors/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InstructorDTO instructorDTO) {

        Instructor instructor = modelMapper.map(instructorDTO, Instructor.class);
        log.debug("REST request to update Instructor : {}, {}", id, instructor);
        if (instructor.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }
        if (!Objects.equals(id, instructor.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }
        if (!instructorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Instructor result = instructorService.save(instructor);
        InstructorDTO instructorDTOResult = modelMapper.map(result, InstructorDTO.class);
        return ResponseEntity
            .ok()
            .body(instructorDTOResult);
    }

    @GetMapping("/instructors")
    public List<Instructor> getAllInstructors() {
        log.debug("REST request to get all Instructors");
        return instructorService.findAllByIsActive(true);
    }

    @GetMapping("/instructors/{id}")
    public ResponseEntity<Instructor> getInstructor(@PathVariable Long id) {
        log.debug("REST request to get Instructor : {}", id);
        Optional<Instructor> instructor = instructorService.findOne(id);
        return ResponseEntity.ok(instructor.get());
    }

    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<Boolean> deleteInstructor(@PathVariable Long id) {
        log.debug("REST request to delete Instructor : {}", id);
        Optional<Instructor> instructor = instructorService.findOne(id);
        if (instructor.isPresent()){
            instructor.get().setIsActive(false);
            instructorService.save(instructor.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
