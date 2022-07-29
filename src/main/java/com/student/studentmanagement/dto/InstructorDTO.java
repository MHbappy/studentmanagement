package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Departments;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class InstructorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    @NotNull
    private String teacherId;
    private Integer age;
    private String address;
    private Boolean isActive;
    private Departments departments;

}
