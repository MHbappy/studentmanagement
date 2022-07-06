package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Assignment}.
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment save(Assignment assignment) {
        log.debug("Request to save Assignment : {}", assignment);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Optional<Assignment> partialUpdate(Assignment assignment) {
        log.debug("Request to partially update Assignment : {}", assignment);

        return assignmentRepository
            .findById(assignment.getId())
            .map(
                existingAssignment -> {
                    if (assignment.getName() != null) {
                        existingAssignment.setName(assignment.getName());
                    }
                    if (assignment.getFile() != null) {
                        existingAssignment.setFile(assignment.getFile());
                    }
                    if (assignment.getFileContentType() != null) {
                        existingAssignment.setFileContentType(assignment.getFileContentType());
                    }
                    if (assignment.getIsActive() != null) {
                        existingAssignment.setIsActive(assignment.getIsActive());
                    }

                    return existingAssignment;
                }
            )
            .map(assignmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> findAll() {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Assignment> findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);
        assignmentRepository.deleteById(id);
    }
}
