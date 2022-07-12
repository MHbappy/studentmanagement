package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Instructor;
import com.student.studentmanagement.repository.InstructorRepository;
import com.student.studentmanagement.service.InstructorService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin@localhost", password = "admin", roles = "ADMIN")
public class InstructorResourceIT {

    @MockBean
    InstructorRepository instructorRepository;

    @Autowired
    InstructorService instructorService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInstructor() throws Exception {
        Departments departments = new Departments();
        departments.setId(1l);
        Instructor instructor = new Instructor(null, "Instructor Name", "str", 20, "", true, departments);
        when(instructorService.save(instructor)).thenReturn(instructor);
        restStudentMockMvc.perform(post("/api/instructors").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnInstructor() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Instructor instructor = new Instructor(null, "Instructor Name", "str", 20, "", true, departments);
        when(instructorRepository.findById(id)).thenReturn(Optional.of(instructor));
        restStudentMockMvc.perform(get("/api/instructors/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void deleteCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Instructor instructor = new Instructor(null, "Instructor Name", "str", 20, "", true, departments);
        when(instructorRepository.findById(id)).thenReturn(Optional.of(instructor));
        restStudentMockMvc.perform(delete("/api/instructors/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void updatedCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Instructor instructorSave = new Instructor(1l, "Instructor Name", "str", 20, "", true, departments);
        Instructor instructorReturn = new Instructor(null, "Instructor another name", "str", 20, "", true, departments);
        when(instructorService.save(instructorSave)).thenReturn(instructorSave);
        when(instructorService.findOne(id)).thenReturn(Optional.of(instructorReturn));
        when(instructorRepository.existsById(id)).thenReturn(true);
        restStudentMockMvc.perform(put("/api/instructors/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructorSave)))
                .andExpect(status().isOk());
    }

}
