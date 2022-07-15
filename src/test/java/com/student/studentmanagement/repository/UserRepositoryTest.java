package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @MockBean
    UserRepository userRepository;

    @Test
    void test_existsByEmail(){
        when(userRepository.existsByEmail("local@local")).thenReturn(true);
        Assertions.assertEquals(true, userRepository.existsByEmail("local@local"));
    }

    @Test
    void test_findByEmail(){
        ArrayList<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.ROLE_CLIENT);
        Users users = new Users(UUID.randomUUID(), "Mehedi", "Hasan", "local@local", "1234", true, true, LocalDateTime.now(), LocalDateTime.now(), userRoles);
        when(userRepository.findByEmail("local@local")).thenReturn(users);
        Assertions.assertEquals(users.getEmail(), userRepository.findByEmail("local@local").getEmail());
    }

}
