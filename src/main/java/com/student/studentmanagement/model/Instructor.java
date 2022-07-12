package com.student.studentmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
/**
 * A Instructor.
 */
@Entity
@Table(name = "instructor")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "teacher_id", nullable = false, unique = true)
    private String teacherId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    private Departments departments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instructor id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Instructor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherId() {
        return this.teacherId;
    }

    public Instructor teacherId(String teacherId) {
        this.teacherId = teacherId;
        return this;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getAge() {
        return this.age;
    }

    public Instructor age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public Instructor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Instructor isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Departments getDepartments() {
        return this.departments;
    }

    public Instructor departments(Departments departments) {
        this.setDepartments(departments);
        return this;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instructor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", teacherId='" + getTeacherId() + "'" +
            ", age=" + getAge() +
            ", address='" + getAddress() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
