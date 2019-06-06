package com.nico.store.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

	ShoppingCart findByUser(User user);
}
