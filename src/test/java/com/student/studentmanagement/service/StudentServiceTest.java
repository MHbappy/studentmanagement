package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.web.servlet.Saml2Dsl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentServiceTest {

    @MockBean
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;

    private Student student;

    List<Student> studentList = new ArrayList<>();

    @BeforeEach
    void setup(){
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";

        student = new Student(1l, "Mehedi", "Hasan", "local@local", "123-35-322", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        studentList.add(student);
        studentList.add(student);
    }

    @Test
    void testSave(){
        student.setEmail("hello@hello");
        when(studentService.save(student)).thenReturn(student);
        Assertions.assertNotNull(student);
    }

    @Test
    void testUpdate(){
        when(studentRepository.findById(1l)).thenReturn(Optional.of(student));
        Optional<Student> student1 = studentService.partialUpdate(student);
        Assertions.assertNotNull(student1);
    }

    @Test
    void testFindAll(){
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> studentList = studentService.findAll();
        Assertions.assertEquals(2, studentList.size());
    }

    @Test
    void testFindOne(){
        when(studentRepository.findById(1l)).thenReturn(Optional.of(student));
        Optional<Student> student = studentService.findOne(1l);
        Assertions.assertNotNull(student);
    }
}
