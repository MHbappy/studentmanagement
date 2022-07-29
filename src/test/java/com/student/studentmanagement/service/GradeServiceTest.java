package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.GradeRepository;
import com.student.studentmanagement.service.impl.GradeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GradeServiceTest {
    @MockBean
    GradeRepository gradeRepository;

    @Autowired
    GradeServiceImpl gradeService;

    private Grade grade;
    List<Grade> gradeList = new ArrayList<>();

    @BeforeEach
    void setup(){
        Student student = new Student();
        student.setId(1l);
        grade = new Grade(1l, "A+", 90, true, student);
        gradeList.add(grade);
        gradeList.add(grade);
    }

    @Test
    void testSave(){
        when(gradeService.save(grade)).thenReturn(grade);
        Assertions.assertNotNull(grade);
    }

    @Test
    void testUpdate(){
        when(gradeRepository.findById(1l)).thenReturn(Optional.of(grade));
        Optional<Grade> grade1 = gradeService.partialUpdate(grade);
        Assertions.assertNotNull(grade1);
    }

    @Test
    void testFindAll(){
        when(gradeRepository.findAll()).thenReturn(gradeList);
        List<Grade> grade1 = gradeService.findAll();
        Assertions.assertEquals(2, grade1.size());
    }

    @Test
    void testFindOne(){
        when(gradeRepository.findById(1l)).thenReturn(Optional.of(grade));
        Optional<Grade> grade = gradeService.findOne(1l);
        Assertions.assertNotNull(grade);
    }
    

}
