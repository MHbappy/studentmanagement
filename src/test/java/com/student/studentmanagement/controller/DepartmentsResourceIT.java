package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.DepartmentsRepository;
import com.student.studentmanagement.repository.GradeRepository;
import com.student.studentmanagement.service.DepartmentsService;
import com.student.studentmanagement.service.GradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin@localhost", password = "admin", roles = "ADMIN")
public class DepartmentsResourceIT {


    @MockBean
    DepartmentsRepository departmentsRepository;

    @Autowired
    DepartmentsService departmentsService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createDepartments() throws Exception {
        Long id = 1l;
        Departments departments = new Departments(null, "CSE", true);
        when(departmentsService.save(departments)).thenReturn(departments);
        restStudentMockMvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departments)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnGrades() throws Exception {
        Long id = 1l;
        Departments departments = new Departments(id, "CSE", true);
        when(departmentsRepository.findById(id)).thenReturn(Optional.of(departments));
        restStudentMockMvc.perform(get("/api/departments/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void deleteGradges() throws Exception {
        Long id = 1l;
        Departments departments = new Departments(id, "CSE", true);
        when(departmentsRepository.findById(id)).thenReturn(Optional.of(departments));
        restStudentMockMvc.perform(delete("/api/departments/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void updatedCourses() throws Exception {
        Long id = 1l;
        Departments departmentsReturn = new Departments(id, "CSE", true);
        Departments departmentsSave = new Departments(id, "BBA", true);
        when(departmentsService.save(departmentsSave)).thenReturn(departmentsSave);
        when(departmentsService.findOne(id)).thenReturn(Optional.of(departmentsReturn));
        when(departmentsRepository.existsById(id)).thenReturn(true);
        restStudentMockMvc.perform(put("/api/departments/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentsSave)))
                .andExpect(status().isOk());
    }

}
