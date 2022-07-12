package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.UserDataDTO;
import com.student.studentmanagement.dto.UserResponseDTO;
import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  @PostMapping("/signin")
  public String login(@RequestParam String username, @RequestParam String password) {
    return userService.signin(username, password);
  }

  @PostMapping("/signup")
  public String signup(@RequestBody UserDataDTO user) {
//    ArrayList<UserRole> userRoles = new ArrayList<>();
//    userRoles.add(UserRole.ROLE_CLIENT);
//    UserDataDTO userDataDTO = new UserDataDTO("Mehedi", "Hasan", "local@local", "1234", userRoles);
//    Users users = new Users(UUID.randomUUID(), "Mehedi", "Hasan", "local@local", "1234", true, true, LocalDateTime.now(), LocalDateTime.now(), userRoles);
    return userService.signup(modelMapper.map(user, Users.class));
  }

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }
}
