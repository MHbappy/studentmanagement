package com.student.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.studentmanagement.dto.UserDataDTO;
import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin@localhost", password = "admin", roles = "ADMIN")
public class UserResourceIT {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBsb2NhbGhvc3QiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2NTc2MzEwMzUsImV4cCI6MTY1Nzk5MTAzNX0.NIUFaqb0gTlXU7Skk1CwJus2fjWr1CS7tfxkheYkt10";

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc restStudentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String userName = "admin@localhost";
    String password = "admin";

    @Test
    void login() throws Exception {
        when(userService.signin(userName, password)).thenReturn(token);
        restStudentMockMvc.perform(post("/users/signin").param("username", userName).param("password", password)).andExpect(status().isOk());
    }

    @Test
    void signUp() throws Exception {
        ArrayList<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.ROLE_CLIENT);
        UserDataDTO userDataDTO = new UserDataDTO("Mehedi", "Hasan", "local@local", "1234", userRoles);
        Users users = new Users(UUID.randomUUID(), "Mehedi", "Hasan", "local@local", "1234", true, true, LocalDateTime.now(), LocalDateTime.now(), userRoles);
        when(userService.signup(users)).thenReturn(token);
        restStudentMockMvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDataDTO))).andExpect(status().isOk());
    }

}
