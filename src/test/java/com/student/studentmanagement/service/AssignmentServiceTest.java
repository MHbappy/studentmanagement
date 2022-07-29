package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Assignment;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.AssignmentRepository;
import com.student.studentmanagement.service.impl.AssignmentServiceImpl;
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
public class AssignmentServiceTest {

    @MockBean
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentServiceImpl assignmentService;

    private Assignment assignment;
    private List<Assignment> assignmentList = new ArrayList<>();

    @BeforeEach
    void setup(){
        Student student = new Student();
        student.setId(1l);
        Course course = new Course();
        course.setId(1l);
        assignment = new Assignment(1l, "assignment1", new byte[2], "pdf", true, course, student);
        assignmentList.add(assignment);
        assignmentList.add(assignment);
    }


    @Test
    void testSave(){
        when(assignmentService.save(assignment)).thenReturn(assignment);
        Assertions.assertNotNull(assignment);
    }

    @Test
    void testUpdate(){
        when(assignmentRepository.findById(1l)).thenReturn(Optional.of(assignment));
        Optional<Assignment> assignment1 = assignmentService.partialUpdate(assignment);
        Assertions.assertNotNull(assignment1);
    }

    @Test
    void testFindAll(){
        when(assignmentRepository.findAllByIsActive(true)).thenReturn(assignmentList);
        when(assignmentRepository.findAll()).thenReturn(assignmentList);
//        List<Assignment> assignment1 = assignmentService.findAllByIsActive(true);
        List<Assignment> assignment2 = assignmentService.findAll();
//        Assertions.assertEquals(2, assignment1.size());
        Assertions.assertEquals(2, assignment2.size());
    }

    @Test
    void testFindOne(){
        when(assignmentRepository.findById(1l)).thenReturn(Optional.of(assignment));
        Optional<Assignment> assignment = assignmentService.findOne(1l);
        Assertions.assertNotNull(assignment);
    }


}
