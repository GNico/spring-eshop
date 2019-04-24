package com.nico.store.store.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
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
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal itemPrice = new BigDecimal(cartItem.getArticle().getPrice()).multiply(new BigDecimal(cartItem.getQty()));
		itemPrice = itemPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(itemPrice);
		cartItemRepository.save(cartItem);
		return cartItem;
	}

	@Override
	public CartItem findById(Long cartItemId) {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		return opt.get();
	}

	@Override
	public CartItem addArticleToCartItem(Article article, User user, int qty, String size) {
		List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());
		for (CartItem cartItem : cartItems) {
			if(article.getId() == cartItem.getArticle().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSize(size);
				cartItem.setSubtotal(new BigDecimal(article.getPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setArticle(article);
		cartItem.setQty(qty);
		cartItem.setSize(size);
		BigDecimal subt = new BigDecimal(article.getPrice()).multiply(new BigDecimal(qty));
		cartItem.setSubtotal(subt);
		System.out.println("el total del cart item es " + subt);
		cartItem = cartItemRepository.save(cartItem);
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
