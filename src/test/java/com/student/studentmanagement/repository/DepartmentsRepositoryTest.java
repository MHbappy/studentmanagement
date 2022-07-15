package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.service.DepartmentsService;
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
public class DepartmentsRepositoryTest {

    @MockBean
    DepartmentsRepository departmentsRepository;

    @Test
    void test_findAllByIsActive(){
        Departments departments1 = new Departments(1l, "CSE", true);
        Departments departments2 = new Departments(2l, "SWE", true);
        ArrayList<Departments> departments = new ArrayList<>();
        departments.add(departments1);
        departments.add(departments2);
        when(departmentsRepository.findAllByIsActive(true)).thenReturn(departments);
        Assertions.assertEquals(2, departmentsRepository.findAllByIsActive(true).size());
    }

    @Test
    void test_existsByNameAndIsActive(){
        when(departmentsRepository.existsByNameAndIsActive("CSE", true)).thenReturn(true);
        Assertions.assertEquals(true, departmentsRepository.existsByNameAndIsActive("CSE", true));
    }




}
