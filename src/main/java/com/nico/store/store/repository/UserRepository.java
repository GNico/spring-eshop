package com.nico.store.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
		
	User findByUsername(String username);
	
	User findByEmail(String email);

}
