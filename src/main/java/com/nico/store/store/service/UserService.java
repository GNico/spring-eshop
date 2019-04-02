package com.nico.store.store.service;

import java.util.Set;

import com.nico.store.store.domain.User;
import com.nico.store.store.domain.security.UserRole;

public interface UserService {
	
	User findById(Long id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;

	void save(User user);

	
}
