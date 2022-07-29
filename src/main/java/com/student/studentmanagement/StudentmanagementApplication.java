package com.student.studentmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.student.studentmanagement.model.UserRole;
import com.student.studentmanagement.model.Users;
import com.student.studentmanagement.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition
public class StudentmanagementApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(StudentmanagementApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public void run(String... params) throws Exception {
		try {
			Users usersAdmin = userService.search("admin@localhost");
			Users user = userService.search("user@localhost");
		}catch (Exception exception){
			Users admin = new Users();
			admin.setFirstname("admin");
			admin.setPassword("admin");
			admin.setEmail("admin@localhost");
			admin.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_ADMIN)));
			userService.signup(admin);

			Users client = new Users();
			client.setFirstname("user");
			client.setPassword("user");
			client.setEmail("user@localhost");
			client.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_CLIENT)));
			userService.signup(client);
		}
	}


}
