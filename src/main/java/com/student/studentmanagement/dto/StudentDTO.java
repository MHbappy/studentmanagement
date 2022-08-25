package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Departments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String studentId;
    private String contactNumber;
    private String address;
    private String dob;
    private Boolean isActive;
    private String useName;
    private String password;
    private Departments departments;
}
