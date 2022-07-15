package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Departments;
import com.student.studentmanagement.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CourseRepositoryTest {

    @MockBean
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Test
    void test_findAllByIsActive(){
        Departments departments = new Departments();
        departments.setId(1l);
        Course coursesReturn1 = new Course(1l, "Courses", "Code 123", true, departments);
        Course coursesReturn2 = new Course(2l, "Courses1", "Code 1231", true, departments);
        Pageable pageable = PageRequest.of(0, 2);

        ArrayList<Course> arrayList = new ArrayList();
        arrayList.add(coursesReturn1);
        arrayList.add(coursesReturn2);

        Page<Course> page = new PageImpl<>(arrayList, pageable, arrayList.size());
        when(courseRepository.findAllByIsActive(true, pageable)).thenReturn(page);

        Assertions.assertEquals(2, courseRepository.findAllByIsActive(true, pageable).getTotalElements());
        Assertions.assertEquals(1, courseRepository.findAllByIsActive(true, pageable).getTotalPages());
    }

    @Test
    void test_findAllByIsActiveAndNameContaining(){
        Departments departments = new Departments();
        departments.setId(1l);
        Course coursesReturn1 = new Course(1l, "Courses", "Code 123", true, departments);
        Course coursesReturn2 = new Course(2l, "Courses1", "Code 1231", true, departments);
        Pageable pageable = PageRequest.of(0, 2);

        ArrayList<Course> arrayList = new ArrayList();
        arrayList.add(coursesReturn1);
        arrayList.add(coursesReturn2);

        Page<Course> page = new PageImpl<>(arrayList, pageable, arrayList.size());
        when(courseRepository.findAllByIsActiveAndNameContaining(true, "Co", pageable)).thenReturn(page);

        Assertions.assertEquals(2, courseRepository.findAllByIsActiveAndNameContaining(true, "Co", pageable).getTotalElements());
        Assertions.assertEquals(1, courseRepository.findAllByIsActiveAndNameContaining(true, "Co", pageable).getTotalPages());
    }


}
