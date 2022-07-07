package com.student.studentmanagement.dto;

import com.student.studentmanagement.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDTO {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  List<UserRole> appUserRoles;
}
