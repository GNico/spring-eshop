package com.nico.store.store;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nico.store.store.domain.User;
import com.nico.store.store.domain.security.Role;
import com.nico.store.store.domain.security.UserRole;
import com.nico.store.store.service.UserService;

import utility.SecurityUtility;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//create admin user if it doesn't exists 
		 if (userService.findByUsername("admin") == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
			admin.setEmail("admin@admin.com");
			Set<UserRole> userRoles = new HashSet<>();
			Role role1 = new Role();
			role1.setRoleId(1);
			role1.setName("ROLE_USER");
			Role role2 = new Role();
			role2.setRoleId(2);
			role2.setName("ROLE_ADMIN");
			userRoles.add(new UserRole(admin, role1));
			userRoles.add(new UserRole(admin, role2));
			userService.createUser(admin, userRoles);
		} 
	}

}
