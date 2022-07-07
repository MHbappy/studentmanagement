package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.UserRole;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDTO {
  private UUID id;
  private String email;
  List<UserRole> appUserRoles;
}
