package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.repository.GradeRepository;
import com.student.studentmanagement.service.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Grade}.
 */
@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private final Logger log = LoggerFactory.getLogger(GradeServiceImpl.class);

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade save(Grade grade) {
        log.debug("Request to save Grade : {}", grade);
        return gradeRepository.save(grade);
    }

    @Override
    public Optional<Grade> partialUpdate(Grade grade) {
        log.debug("Request to partially update Grade : {}", grade);

        return gradeRepository
            .findById(grade.getId())
            .map(
                existingGrade -> {
                    if (grade.getName() != null) {
                        existingGrade.setName(grade.getName());
                    }
                    if (grade.getNumber() != null) {
                        existingGrade.setNumber(grade.getNumber());
                    }
                    if (grade.getIsActive() != null) {
                        existingGrade.setIsActive(grade.getIsActive());
                    }

                    return existingGrade;
                }
            )
            .map(gradeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findAll() {
        log.debug("Request to get all Grades");
        return gradeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grade> findOne(Long id) {
        log.debug("Request to get Grade : {}", id);
        return gradeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grade : {}", id);
        gradeRepository.deleteById(id);
    }
}
