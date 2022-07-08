package com.student.studentmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

/**
 * A Departments.
 */
@Entity
@Table(name = "departments")
@Data
@ToString
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

}
