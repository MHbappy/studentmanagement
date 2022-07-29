package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.GradeServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GradeRepositoryTest {

    @MockBean
    GradeRepository gradeRepository;

    @Autowired
    GradeServiceTest gradeService;

    @Test
    void test_findAllByIsActive(){
        Student student = new Student();
        student.setId(1l);

        Grade grade1 = new Grade(1l, "A+", 90, true, student);
        Grade grade2 = new Grade(2l, "A+", 90, true, student);

        ArrayList<Grade> grades = new ArrayList<>();
        grades.add(grade1);
        grades.add(grade2);

        when(gradeRepository.findAllByIsActive(true)).thenReturn(grades);
        Assertions.assertEquals(2, gradeRepository.findAllByIsActive(true).size());
    }

}
