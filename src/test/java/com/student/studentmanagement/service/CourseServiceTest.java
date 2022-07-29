package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.service.impl.CourseServiceImpl;
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
public class CourseServiceTest {

    @MockBean
    CourseRepository courseRepository;

    @Autowired
    CourseServiceImpl courseService;
    
    private Course course;
    
    List<Course> courseList = new ArrayList<>();

    @BeforeEach
    void setup(){
        Departments departments = new Departments();
        departments.setId(1l);
        course = new Course(1l, "Courses", "Code 123", true, departments);
        courseList.add(course);
        courseList.add(course);
    }


    @Test
    void testSave(){
        when(courseService.save(course)).thenReturn(course);
        Assertions.assertNotNull(course);
    }

    @Test
    void testUpdate(){
        when(courseRepository.findById(1l)).thenReturn(Optional.of(course));
        Optional<Course> assignment1 = courseService.partialUpdate(course);
        Assertions.assertNotNull(assignment1);
    }

    @Test
    void testFindAll(){
        when(courseRepository.findAll()).thenReturn(courseList);
        List<Course> assignment2 = courseService.findAll();
        Assertions.assertEquals(2, assignment2.size());
    }

    @Test
    void testFindOne(){
        when(courseRepository.findById(1l)).thenReturn(Optional.of(course));
        Optional<Course> course = courseService.findOne(1l);
        Assertions.assertNotNull(course);
    }
}
