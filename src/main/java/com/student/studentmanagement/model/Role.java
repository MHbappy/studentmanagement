package com.student.studentmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "role")
@Data
@ToString
public class Role {
    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;
}
