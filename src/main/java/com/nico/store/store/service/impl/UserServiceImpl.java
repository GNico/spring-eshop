package com.nico.store.store.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.domain.security.UserRole;
import com.nico.store.store.repository.RoleRepository;
import com.nico.store.store.repository.UserRepository;
import com.nico.store.store.service.UserService;

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
	public User createUser(User user, Set<UserRole> userRoles) throws Exception  {
		User localUser = userRepository.findByUsername(user.getUsername());
		if (localUser != null) {
			throw new Exception("User already exists. Nothing done");
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
						
			localUser = userRepository.save(user);
		}
		return localUser;
	}

	
	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	

	
}
