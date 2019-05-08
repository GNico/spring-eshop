package com.nico.store.store.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.domain.security.Role;
import com.nico.store.store.domain.security.UserRole;
import com.nico.store.store.repository.RoleRepository;
import com.nico.store.store.repository.UserRepository;
import com.nico.store.store.service.UserService;

import utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findById(Long id) {
		Optional<User> opt = userRepository.findById(id);
		return opt.get();
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User createAdmin() {
		User existingAdmin = findByUsername("admin");
		if (existingAdmin != null) {
			return existingAdmin;
		} else {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
			admin.setEmail("admin@admin.com");
			Set<UserRole> userRoles = new HashSet<>();
			Role role1 = new Role();
			role1.setRoleId(1);
			role1.setName("ROLE_USER");
			roleRepository.save(role1);
			Role role2 = new Role();
			role2.setRoleId(2);
			role2.setName("ROLE_ADMIN");
			roleRepository.save(role2);
			userRoles.add(new UserRole(admin, role1));
			userRoles.add(new UserRole(admin, role2));
			admin.setUserRoles(userRoles);			
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(admin);
			admin.setShoppingCart(shoppingCart);						
			return userRepository.save(admin);
		}
	}
	
	public User createBasicUser(String username, String email,  String password) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(SecurityUtility.passwordEncoder().encode(password));			
			Role role = new Role();
			role.setRoleId(1);
			role.setName("ROLE_USER");
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user,role));
			user.setUserRoles(userRoles);
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
			return userRepository.save(user);
		}		
	}

	
	

	
}
