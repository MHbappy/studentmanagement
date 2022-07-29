package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.security.SecurityUtils;
import com.student.studentmanagement.service.AssignmentService;
import com.student.studentmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Assignment}.
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    private final UserService userService;

    private final StudentRepository studentRepository;

    private final SecurityUtils securityUtils;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, UserService userService, StudentRepository studentRepository, SecurityUtils securityUtils) {
        this.assignmentRepository = assignmentRepository;
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.securityUtils = securityUtils;
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
    public List<Assignment> findAllByIsActive(Boolean isActive, HttpServletRequest req) {
//        Users users = userService.whoami(req);
//        if (users.getAppUserRoles().contains(UserRole.ROLE_CLIENT)){
////            studentRepository.findAllByIsActiveAndFirstNameLike()
//        }
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
//        Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());

//        securityUtils.getUserName()
        Student student = studentRepository.findByUseName(securityUtils.getUserName());
        if (securityUtils.getRoles().contains("ROLE_CLIENT")){
            return assignmentRepository.findAllByStudentAndIsActive(student, true);
        }
        return assignmentRepository.findAllByIsActive(isActive);
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
