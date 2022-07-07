package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.UserDataDTO;
import com.student.studentmanagement.dto.UserResponseDTO;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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
    return userService.signup(modelMapper.map(user, Users.class));
  }

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }
}
