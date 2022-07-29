package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Departments;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
//@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String studentId;
    private String contactNumber;
    private String address;
    private LocalDate dob;
    private Boolean isActive;
    private String useName;
    private String password;
    private Departments departments;
}
