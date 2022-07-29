package com.student.studentmanagement.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SecurityUtils {

    public String getUserName(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return username;
    }

    public Set<String> getRoles(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
        return roles;
    }

}
