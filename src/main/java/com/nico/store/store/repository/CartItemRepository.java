package com.nico.store.store.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
