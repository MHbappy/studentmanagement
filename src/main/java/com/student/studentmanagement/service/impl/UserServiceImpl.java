package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.exception.CustomException;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.repository.UserRepository;
import com.student.studentmanagement.security.JwtTokenProvider;
import com.student.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByEmailAndIsActive(username, true).getAppUserRoles());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password supplied");
        }
    }

    public String signup(Users appUser) {
        if (!userRepository.existsByEmailAndIsActive(appUser.getEmail(), true)) {
            appUser.setIsActive(true);
            appUser.setIsVerified(true);
            appUser.setUpdatedAt(LocalDateTime.now());
            appUser.setCreatedAt(LocalDateTime.now());
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
//      return "Account registered";
            return jwtTokenProvider.createToken(appUser.getEmail(), appUser.getAppUserRoles());
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username is already in use");
        }
    }

    public void inactiveUser(Users users){
        users.setIsActive(false);
        userRepository.save(users);
    }

    public void delete(String username) {
        userRepository.deleteByEmail(username);
    }

    public Users search(String username) {
        Users appUser = userRepository.findByEmail(username);
        if (appUser == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return appUser;
    }

    public Users whoami(HttpServletRequest req) {
        return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    @Override
    public Users findByEmailAndIsActive(String email, Boolean isActive) {
        return userRepository.findByEmailAndIsActive(email, isActive);
    }
}
