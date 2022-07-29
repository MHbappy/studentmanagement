package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.repository.DepartmentsRepository;
import com.student.studentmanagement.service.impl.DepartmentsServiceImpl;
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
public class DepartmentsServiceTest {

    @MockBean
    DepartmentsRepository departmentsRepository;

    @Autowired
    DepartmentsServiceImpl departmentsService;

    private Departments departments;
    List<Departments> departmentsList = new ArrayList<>();

    @BeforeEach
    void setup(){
        departments = new Departments(1l, "CSE", true);
        departmentsList.add(departments);
        departmentsList.add(departments);
    }


    @Test
    void testSave(){
        when(departmentsService.save(departments)).thenReturn(departments);
        Assertions.assertNotNull(departments);
    }

    @Test
    void testUpdate(){
        when(departmentsRepository.findById(1l)).thenReturn(Optional.of(departments));
        Optional<Departments> departments1 = departmentsService.partialUpdate(departments);
        Assertions.assertNotNull(departments1);
    }

    @Test
    void testFindAll(){
        when(departmentsRepository.findAll()).thenReturn(departmentsList);
        List<Departments> departments1 = departmentsService.findAll();
        Assertions.assertEquals(2, departments1.size());
    }

    @Test
    void testFindOne(){
        when(departmentsRepository.findById(1l)).thenReturn(Optional.of(departments));
        Optional<Departments> departments = departmentsService.findOne(1l);
        Assertions.assertNotNull(departments);
    }



}
