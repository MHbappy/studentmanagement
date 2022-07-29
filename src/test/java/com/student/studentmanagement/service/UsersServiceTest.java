package com.student.studentmanagement.service;

import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.repository.UserRepository;
import com.student.studentmanagement.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsersServiceTest {

    @MockBean
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager authenticationManager;

    Users users;
    List<UserRole> appUserRoles = new ArrayList<>();

    @BeforeEach
    void setup(){
        appUserRoles.add(UserRole.ROLE_CLIENT);
        users = new Users(UUID.randomUUID(), "mehedi", "hasan", "local@local", "11111", true, true, LocalDateTime.now(), LocalDateTime.now(), appUserRoles);
    }

    @Test
    void testSignUp(){
        when(userRepository.existsByEmail("local@local")).thenReturn(false);
        when(passwordEncoder.encode("11111")).thenReturn("11111");
        when(userRepository.save(users)).thenReturn(users);
        when(jwtTokenProvider.createToken("local@local", appUserRoles)).thenReturn("token");
        String token = userService.signup(users);
        Assertions.assertEquals("token", token);
    }

    @Test
    void testSearch(){
        when(userRepository.findByEmail("local@local")).thenReturn(users);
        Users search = userService.search("local@local");
        Assertions.assertNotNull(search);
    }

}
