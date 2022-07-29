package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.AssignmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AssignmentRepositoryTest {

    @MockBean
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_findAllByIsActive(){
        Student student = new Student();
        student.setId(1l);

        Optional<Course> course = courseRepository.findById(1l);

        Assignment assignment1 = new Assignment(1l, "assignment1", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        Assignment assignment2 = new Assignment(1l, "assignment2", new byte[2], "pdf", true, course.isEmpty() ? null : course.get(), student);
        ArrayList<Assignment> arrayList = new ArrayList();
        arrayList.add(assignment1);
        arrayList.add(assignment2);
        when(assignmentRepository.findAllByIsActive(true)).thenReturn(arrayList);
        Assertions.assertEquals(2, arrayList.size());
    }

}
