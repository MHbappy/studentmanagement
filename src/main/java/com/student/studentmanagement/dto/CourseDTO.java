package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Departments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    @NotNull
    private String code;
    private Boolean isActive;
    Departments departments;
}
