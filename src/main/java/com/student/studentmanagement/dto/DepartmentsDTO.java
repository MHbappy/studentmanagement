package com.student.studentmanagement.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentsDTO {
    private Long id;
    @NotNull
    private String name;
    private Boolean isActive;
}
