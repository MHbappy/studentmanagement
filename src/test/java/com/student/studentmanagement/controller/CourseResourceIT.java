package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.service.CourseService;
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
public class CourseResourceIT {

    @MockBean
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Course CoursesReturn = new Course(null, "Courses", "Code 123", true, departments);
        when(courseService.save(CoursesReturn)).thenReturn(CoursesReturn);
        restStudentMockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CoursesReturn)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Course coursesReturn = new Course(null, "Courses", "Code 123", true, departments);
        when(courseRepository.findById(id)).thenReturn(Optional.of(coursesReturn));
        restStudentMockMvc.perform(get("/api/courses/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void deleteCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Course coursesReturn = new Course(null, "Courses", "Code 123", true, departments);
        when(courseRepository.findById(id)).thenReturn(Optional.of(coursesReturn));
        restStudentMockMvc.perform(delete("/api/courses/{id}", id)).andExpect(status().isOk());
    }


    @Test
    void updatedCourses() throws Exception {
        Long id = 1l;
        Departments departments = new Departments();
        departments.setId(1l);
        Course coursesReturn = new Course(1l, "Courses", "Code 123", true, departments);
        Course coursesSave = new Course(1l, "Courses 1", "Code 1567", true, departments);
        when(courseService.save(coursesSave)).thenReturn(coursesSave);
        when(courseService.findOne(id)).thenReturn(Optional.of(coursesReturn));
        when(courseRepository.existsById(id)).thenReturn(true);
        restStudentMockMvc.perform(put("/api/courses/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursesSave)))
                .andExpect(status().isOk());
    }
    
    
}
