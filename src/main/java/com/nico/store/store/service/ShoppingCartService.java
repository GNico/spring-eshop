package com.nico.store.store.service;

import com.nico.store.store.domain.ShoppingCart;


public interface ShoppingCartService {

	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);
	
}
