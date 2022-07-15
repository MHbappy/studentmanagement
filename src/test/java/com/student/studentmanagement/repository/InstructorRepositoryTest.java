package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.InstructorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InstructorRepositoryTest {

    @MockBean
    InstructorRepository instructorRepository;

    @Autowired
    InstructorService instructorService;

    @Test
    void test_findAllByIsActive(){
        Departments departments = new Departments();
        departments.setId(1l);

        Instructor instructor1 = new Instructor(1l, "Instructor Name1", "str1", 20, "", true, departments);
        Instructor instructor2 = new Instructor(2l, "Instructor Name2", "str2", 20, "", true, departments);

        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor1);
        instructors.add(instructor2);
        when(instructorRepository.findAllByIsActive(true)).thenReturn(instructors);
        Assertions.assertEquals(2, instructorRepository.findAllByIsActive(true).size());
    }

    @Test
    void test_existsByTeacherId(){
        when(instructorRepository.existsByTeacherId("123")).thenReturn(true);
        Assertions.assertEquals(true, instructorRepository.existsByTeacherId("123"));
    }

}
