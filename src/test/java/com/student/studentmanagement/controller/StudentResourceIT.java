package com.student.studentmanagement.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin@localhost", password = "admin", roles = "ADMIN")
public class StudentResourceIT {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStudent() throws Exception {
        Departments departments = new Departments();
        departments.setId(1l);
        Student student = new Student(1l, "Mehedi", "123-35-322", "88888888", "address", 12, true, departments);
        when(studentService.save(student)).thenReturn(student);

        restStudentMockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturenStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Student student = new Student(id, "Mehedi", "123-35-322", "88888888", "address", 12, true, departments);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        restStudentMockMvc.perform(get("/api/students/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void deleteStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Student student = new Student(id, "Mehedi", "123-35-322", "88888888", "address", 12, true, departments);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        restStudentMockMvc.perform(delete("/api/students/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void updatedStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Student studentExist = new Student(id, "Mehedi", "123-35-322", "88888888", "address", 12, true, departments);
        Student studentUpdate = new Student(id, "Bappy", "123-35-322", "88888888", "address", 12, true, departments);
        when(studentService.save(studentUpdate)).thenReturn(studentUpdate);
        when(studentRepository.findById(id)).thenReturn(Optional.of(studentUpdate));
        restStudentMockMvc.perform(get("/api/students/{id}", id)).andExpect(status().isOk());
    }


}
