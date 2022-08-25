package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO {
    private Long id;
    private String name;
    private Integer number;
    private Boolean isActive;
    private Student student;
}