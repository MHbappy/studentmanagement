package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Grade;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.GradeRepository;
import com.student.studentmanagement.service.GradeService;
import com.student.studentmanagement.service.GradeServiceTest;
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
public class GradeResourceIT {

    @MockBean
    GradeRepository gradeRepository;

    @Autowired
    GradeService gradeService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCourses() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Grade grade = new Grade(null, "A+", 90, true, student);
        when(gradeService.save(grade)).thenReturn(grade);
        restStudentMockMvc.perform(post("/api/grades").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(grade)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnGradges() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Grade grade = new Grade(null, "A+", 90, true, student);
        when(gradeRepository.findById(id)).thenReturn(Optional.of(grade));
        restStudentMockMvc.perform(get("/api/grades/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void deleteGradges() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Grade grade = new Grade(1l, "A+", 90, true, student);
        when(gradeRepository.findById(id)).thenReturn(Optional.of(grade));
        restStudentMockMvc.perform(delete("/api/grades/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void updatedCourses() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Grade gradeReturn = new Grade(1l, "A+", 90, true, student);
        Grade gradeUpdateSave = new Grade(1l, "B+", 90, true, student);
        when(gradeService.save(gradeUpdateSave)).thenReturn(gradeUpdateSave);
        when(gradeService.findOne(id)).thenReturn(Optional.of(gradeReturn));
        when(gradeRepository.existsById(id)).thenReturn(true);
        restStudentMockMvc.perform(put("/api/grades/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gradeUpdateSave)))
                .andExpect(status().isOk());
    }

}
