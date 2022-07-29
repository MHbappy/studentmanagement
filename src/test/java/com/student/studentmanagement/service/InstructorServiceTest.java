package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.repository.InstructorRepository;
import com.student.studentmanagement.service.impl.InstructorServiceImpl;
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
public class InstructorServiceTest {
    @MockBean
    InstructorRepository instructorRepository;

    @Autowired
    InstructorServiceImpl instructorService;

    private Instructor instructor;
    List<Instructor> instructorList = new ArrayList<>();

    @BeforeEach
    void setup(){
        Departments departments = new Departments();
        departments.setId(1l);
        instructor = new Instructor(1l, "Instructor1", "Name1", "local@local", "0000000", "str1", 20, "", true, departments);
        instructorList.add(instructor);
        instructorList.add(instructor);
    }

    @Test
    void testSave(){
        when(instructorService.save(instructor)).thenReturn(instructor);
        Assertions.assertNotNull(instructor);
    }

    @Test
    void testUpdate(){
        when(instructorRepository.findById(1l)).thenReturn(Optional.of(instructor));
        Optional<Instructor> instructor1 = instructorService.partialUpdate(instructor);
        Assertions.assertNotNull(instructor1);
    }

    @Test
    void testFindAll(){
        when(instructorRepository.findAll()).thenReturn(instructorList);
        List<Instructor> instructorList = instructorService.findAll();
        Assertions.assertEquals(2, instructorList.size());
    }

    @Test
    void testFindOne(){
        when(instructorRepository.findById(1l)).thenReturn(Optional.of(instructor));
        Optional<Instructor> instructor = instructorService.findOne(1l);
        Assertions.assertNotNull(instructor);
    }


}
