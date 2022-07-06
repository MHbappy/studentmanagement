package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.repository.DepartmentsRepository;
import com.student.studentmanagement.service.DepartmentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Departments}.
 */
@Service
@Transactional
public class DepartmentsServiceImpl implements DepartmentsService {

    private final Logger log = LoggerFactory.getLogger(DepartmentsServiceImpl.class);

    private final DepartmentsRepository departmentsRepository;

    public DepartmentsServiceImpl(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    @Override
    public Departments save(Departments departments) {
        log.debug("Request to save Departments : {}", departments);
        return departmentsRepository.save(departments);
    }

    @Override
    public Optional<Departments> partialUpdate(Departments departments) {
        log.debug("Request to partially update Departments : {}", departments);

        return departmentsRepository
            .findById(departments.getId())
            .map(
                existingDepartments -> {
                    if (departments.getName() != null) {
                        existingDepartments.setName(departments.getName());
                    }
                    if (departments.getIsActive() != null) {
                        existingDepartments.setIsActive(departments.getIsActive());
                    }

                    return existingDepartments;
                }
            )
            .map(departmentsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departments> findAll() {
        log.debug("Request to get all Departments");
        return departmentsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Departments> findOne(Long id) {
        log.debug("Request to get Departments : {}", id);
        return departmentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Departments : {}", id);
        departmentsRepository.deleteById(id);
    }
}
