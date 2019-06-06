package com.nico.store.store.service;

import java.util.List;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;


public interface ShoppingCartService {

	List<CartItem> getCartItems(User user);
	
	CartItem findById(Long cartItemId);
	
	CartItem addArticleToCartItem(Article article, ShoppingCart shoppingCart, int qty, String size);
	
	CartItem save(CartItem cartItem);
	
	void clearShoppingCart(ShoppingCart shoppingCart);

	void removeCartItem(ShoppingCart cart, CartItem cartItem);
	
}
