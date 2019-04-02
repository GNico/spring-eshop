package com.nico.store.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

	
}
