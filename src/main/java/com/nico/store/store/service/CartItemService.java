package com.nico.store.store.service;

import java.util.List;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	void removeCartItem(CartItem cartItem);

	CartItem findById(Long cartItemId);
	
	CartItem addArticleToCartItem(Article article, User user, int qty);
	
	CartItem save(CartItem cartItem);
}
