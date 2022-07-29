package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.service.AssignmentService;
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
public class AssignmentResourceIT {

    @MockBean
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    void createAssignment() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Optional<Course> course = courseRepository.findById(1l);
        Course course1 = new Course();
        course1.setId(1l);

        Assignment assignmentSave = new Assignment(1l, "assignment", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        Assignment assignmentReturn = new Assignment(null, "assignment", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        when(assignmentService.save(assignmentReturn)).thenReturn(assignmentReturn);

        restStudentMockMvc.perform(post("/api/assignments").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentReturn)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnAssignment() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Optional<Course> course = courseRepository.findById(1l);

        Assignment assignmentSave = new Assignment(id, "assignment", new byte[2], "pdf", true,course.isEmpty() ? null : course.get(), student);
        when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignmentSave));
        restStudentMockMvc.perform(get("/api/assignments/{id}", id)).andExpect(status().isOk());
    }



    @Test
    void deleteAssignment() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Optional<Course> course = courseRepository.findById(1l);

        Assignment assignmentDelete = new Assignment(id, "assignment", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignmentDelete));
        restStudentMockMvc.perform(delete("/api/assignments/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void updatedAssignment() throws Exception {
        Long id = 1l;
        Student student = new Student();
        student.setId(1l);
        Optional<Course> course = courseRepository.findById(1l);

        Assignment assignmentSave = new Assignment(1l, "assignment", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        Assignment studentUpdate = new Assignment(1l, "assignment123", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        when(assignmentService.save(assignmentSave)).thenReturn(assignmentSave);
        when(assignmentService.findOne(id)).thenReturn(Optional.of(studentUpdate));
        when(assignmentRepository.existsById(id)).thenReturn(true);
        restStudentMockMvc.perform(put("/api/assignments/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentSave))).andExpect(status().isOk());
    }

}
