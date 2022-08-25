package com.student.studentmanagement.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.dto.StudentDTO;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.repository.UserRepository;
import com.student.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.time.LocalDate;
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

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createStudent() throws Exception {
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";
        Student student2 = new Student(null, "Mehedi", "Hasan", "local@local1", "123-35-322", "88888888", "address", null, true, userName, "123", departments);
        StudentDTO studentDTO = new StudentDTO(null, "Mehedi", "Hasan", "local@local1", "123-35-322", "88888888", "address", null, true, userName, "123", departments);
        when(studentService.save(student2)).thenReturn(student2);
        restStudentMockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturenStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";
        Student student = new Student(id, "Mehedi", "Hasan", "local@local.com", "123-35-322", "88888888", "address", null, true, userName, "pass", departments);
        when(studentService.findOne(id)).thenReturn(Optional.of(student));
        restStudentMockMvc.perform(get("/api/students/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void deleteStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user@user";
        Student student = new Student(id, "Mehedi", "Hasan", "local@local", "123-35-322", "88888888", "address", LocalDate.now(), true, userName, "pass", departments);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        restStudentMockMvc.perform(delete("/api/students/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void updatedStudent() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        String userName = "user1@user1";
        Student studentUpdate = new Student(id, "Mehedi", "Hasan", "local@local", "123-35-322", "88888888", "address", null, true, userName, "pass", departments);
        when(studentService.updateSave(studentUpdate)).thenReturn(studentUpdate);
        when(studentService.findOne(id)).thenReturn(Optional.of(studentUpdate));
        restStudentMockMvc.perform(get("/api/students/{id}", id)).andExpect(status().isOk());
    }


}
