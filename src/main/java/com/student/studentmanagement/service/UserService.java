package com.student.studentmanagement.service;

import com.student.studentmanagement.model.Users;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {
  String signin(String username, String password);
  String signup(Users appUser);
  void delete(String username);
  Users search(String username);
  Users whoami(HttpServletRequest req);
  void inactiveUser(Users users);
  Users findByEmailAndIsActive(String email, Boolean isActive);
}
