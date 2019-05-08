package com.nico.store.store.service;


import com.nico.store.store.domain.User;

public interface UserService {
	
	User findById(Long id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
		
	void save(User user);
	
	User createBasicUser(String username, String email,  String password);

	User createAdmin();
}
