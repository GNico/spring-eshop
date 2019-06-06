package com.nico.store.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.repository.CartItemRepository;
import com.nico.store.store.repository.ShoppingCartRepository;
import com.nico.store.store.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	
	@Override
	public List<CartItem> getCartItems(User user) {
		ShoppingCart sc = shoppingCartRepository.findByUser(user);
		return sc.getCartItems();
	}


	@Override
	public CartItem findById(Long cartItemId) {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		return opt.get();
	}

	@Override
	public CartItem addArticleToCartItem(Article article, ShoppingCart shoppingCart, int qty, String size) {
		CartItem cartItem = shoppingCart.findCartItemByArticle(article.getId());
		if (cartItem != null) {
			cartItem.addQuantity(qty);
			cartItem.setSize(size);
			cartItem = cartItemRepository.save(cartItem);
		} else {
			cartItem = new CartItem();
			cartItem.setShoppingCart(shoppingCart);
			cartItem.setArticle(article);
			cartItem.setQty(qty);
			cartItem.setSize(size);
			cartItem = cartItemRepository.save(cartItem);
		}		
		return cartItem;	
	}

	@Override
	public void removeCartItem(ShoppingCart shoppingCart, CartItem cartItem) {
		shoppingCart.removeCartItem(cartItem);
		shoppingCartRepository.save(shoppingCart);
		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}
	

	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		for (CartItem item : shoppingCart.getCartItems()) {
			item.setShoppingCart(null);
			cartItemRepository.save(item);
		}
		shoppingCart.clearItems();
		shoppingCartRepository.save(shoppingCart);

	}
	

}
