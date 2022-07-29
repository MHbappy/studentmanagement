package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentRepositoryTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void test_existsByContactNumber(){
        when(studentRepository.existsByContactNumber("123")).thenReturn(true);
        Assertions.assertEquals(true, studentRepository.existsByContactNumber("123"));
    }

    @Test
    void test_existsByStudentId(){
        when(studentRepository.existsByStudentId("123")).thenReturn(true);
        Assertions.assertEquals(true, studentRepository.existsByStudentId("123"));
    }

    @Test
    void test_findAllByIsActive(){
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";

        Student student1 = new Student(1l, "Mehedi", "Hasan", "local@local", "123-35-322", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        Student student2 = new Student(2l, "Mehedi1", "Hasan1", "local@local", "123-35-321", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAllByIsActive(true)).thenReturn(studentList);
        Assertions.assertEquals(2, studentRepository.findAllByIsActive(true).size());
    }

    @Test
    void test_findAllByIsActiveAndNameContaining(){

        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";

        Student student1 = new Student(1l, "Mehedi", "Hasan", "local@local", "123-35-322", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        Student student2 = new Student(2l, "Mehedi1", "Hasan1", "local@local", "123-35-321", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        Pageable pageable = PageRequest.of(0, 2);


        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        Page<Student> page = new PageImpl<>(studentList, pageable, studentList.size());

//
//        when(studentRepository.findAllByIsActiveAndFirstNameContainingIgnoreCase(true, "Me", pageable)).thenReturn(page);
//        Assertions.assertEquals(2, studentRepository.findAllByIsActiveAndFirstNameContainingIgnoreCase(true, "Me", pageable).getTotalElements());

    }
}
