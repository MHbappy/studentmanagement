package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.GradeDTO;
import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.repository.GradeRepository;
import com.student.studentmanagement.service.GradeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class GradeResource {

    private final Logger log = LoggerFactory.getLogger(GradeResource.class);
    private final GradeService gradeService;
    private final GradeRepository gradeRepository;
    private final ModelMapper modelMapper;


    public GradeResource(GradeService gradeService, GradeRepository gradeRepository, ModelMapper modelMapper) {
        this.gradeService = gradeService;
        this.gradeRepository = gradeRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/grades")
    public ResponseEntity<GradeDTO> createGrade(@Valid @RequestBody GradeDTO gradeDTO) throws URISyntaxException {
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        log.debug("REST request to save Grade : {}", grade);
        if (grade.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new grade cannot already have an ID");
        }
        Grade result = gradeService.save(grade);

        GradeDTO gradeDTOResult =  modelMapper.map(result, GradeDTO.class);
        return ResponseEntity
            .created(new URI("/api/grades/" + gradeDTOResult.getId()))
            .body(gradeDTOResult);
    }
    
    @PutMapping("/grades/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody GradeDTO gradeDTO)
        throws URISyntaxException {
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        log.debug("REST request to update Grade : {}, {}", id, grade);
        if (grade.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }
        if (!Objects.equals(id, grade.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        if (!gradeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Grade result = gradeService.save(grade);
        GradeDTO gradeDTOResult =  modelMapper.map(result, GradeDTO.class);
        return ResponseEntity
            .ok()
            .body(gradeDTOResult);
    }
    
    @GetMapping("/grades")
    public List<Grade> getAllGrades() {
        log.debug("REST request to get all Grades");
        return gradeService.findAllByIsActive(true);
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<Grade> getGrade(@PathVariable Long id) {
        log.debug("REST request to get Grade : {}", id);
        Optional<Grade> grade = gradeService.findOne(id);
        return ResponseEntity.ok(grade.get());
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Boolean> deleteGrade(@PathVariable Long id) {
        log.debug("REST request to delete Grade : {}", id);
        Optional<Grade> grade = gradeService.findOne(id);
        if (grade.isPresent()){
            grade.get().setIsActive(false);
            gradeService.save(grade.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
