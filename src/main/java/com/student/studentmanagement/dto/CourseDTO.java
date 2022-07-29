package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Departments;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CourseDTO {
    private Long id;
    private String name;
    @NotNull
    private String code;
    private Boolean isActive;
    Departments departments;
}
