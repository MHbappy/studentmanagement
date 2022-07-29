package com.student.studentmanagement.controller;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);
    private final AssignmentService assignmentService;
    private final AssignmentRepository assignmentRepository;

    public AssignmentResource(AssignmentService assignmentService, AssignmentRepository assignmentRepository) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
    }

    @PostMapping("/assignments")
    public ResponseEntity<Assignment> createAssignment(@Valid  @RequestBody Assignment assignment, Authentication authentication) throws URISyntaxException {
        log.debug("REST request to save Assignment : {}", assignment);
        if (assignment.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new assignment cannot already have an ID");
        }
        String name = authentication.getName();

        Assignment result = assignmentService.save(assignment);
        return ResponseEntity
                .created(new URI("/api/assignments/" + result.getId()))
                .body(result);
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody Assignment assignment
    ) {
        log.debug("REST request to update Assignment : {}, {}", id, assignment);
        if (assignment.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, assignment.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }
        Assignment result = assignmentService.save(assignment);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/assignments")
    public List<Assignment> getAllAssignments(HttpServletRequest req) {
        log.debug("REST request to get all Assignments");
        return assignmentService.findAllByIsActive(true, req);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<Assignment> assignment = assignmentService.findOne(id);
        return ResponseEntity.ok(assignment.get());
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Boolean> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        Optional<Assignment> assignment = assignmentService.findOne(id);

        if (assignment.isPresent()){
            assignment.get().setIsActive(false);
            assignmentService.save(assignment.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
