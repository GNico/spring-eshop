package com.nico.store.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.repository.CartItemRepository;
import com.nico.store.store.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}


	@Override
	public CartItem findById(Long cartItemId) {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		return opt.get();
	}

	@Override
	public CartItem addArticleToCartItem(Article article, ShoppingCart shoppingCart, int qty, String size) {
		//List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());
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
	public void removeCartItem(CartItem cartItem) {
		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}
	
	

}
