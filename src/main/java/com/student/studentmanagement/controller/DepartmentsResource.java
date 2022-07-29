package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.DepartmentsDTO;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.repository.DepartmentsRepository;
import com.student.studentmanagement.service.DepartmentsService;
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
public class DepartmentsResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentsResource.class);
    private final DepartmentsService departmentsService;
    private final DepartmentsRepository departmentsRepository;
    private final ModelMapper modelMapper;


    public DepartmentsResource(DepartmentsService departmentsService, DepartmentsRepository departmentsRepository, ModelMapper modelMapper) {
        this.departmentsService = departmentsService;
        this.departmentsRepository = departmentsRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentsDTO> createDepartments(@Valid @RequestBody DepartmentsDTO departmentsDTO) throws URISyntaxException {

        Departments departments = modelMapper.map(departmentsDTO, Departments.class);
        log.debug("REST request to save Departments : {}", departments);
        if (departments.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new departments cannot already have an ID");
        }

        if (departmentsRepository.existsByNameAndIsActive(departments.getName(), true)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department can not be duplicate");
        }

        Departments result = departmentsService.save(departments);
        DepartmentsDTO departmentsDTOResult = modelMapper.map(result, DepartmentsDTO.class);
        return ResponseEntity
            .created(new URI("/api/departments/" + departmentsDTOResult.getId()))
            .body(departmentsDTOResult);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentsDTO> updateDepartments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartmentsDTO departmentsDTO
    ) {
        Departments departments = modelMapper.map(departmentsDTO, Departments.class);
        log.debug("REST request to update Departments : {}, {}", id, departments);
        if (departments.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, departments.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!departmentsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        Departments result = departmentsService.save(departments);
        DepartmentsDTO departmentsDTOResult = modelMapper.map(result, DepartmentsDTO.class);
        return ResponseEntity
            .ok()
            .body(departmentsDTOResult);
    }
    
    @GetMapping("/departments")
    public List<Departments> getAllDepartments() {
        log.debug("REST request to get all Departments");
        return departmentsService.findAllByIsActive(true);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Departments> getDepartments(@PathVariable Long id) {
        log.debug("REST request to get Departments : {}", id);
        Optional<Departments> departments = departmentsService.findOne(id);
        return ResponseEntity.ok(departments.get());
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Boolean> deleteDepartments(@PathVariable Long id) {
        log.debug("REST request to delete Departments : {}", id);
        Optional<Departments> assignment = departmentsService.findOne(id);
        if (assignment.isPresent()){
            assignment.get().setIsActive(false);
            departmentsService.save(assignment.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
