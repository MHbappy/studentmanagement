package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);

    Users findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}
