package com.student.studentmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;
import lombok.ToString;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Data
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "is_active")
    private Boolean isActive;
}
