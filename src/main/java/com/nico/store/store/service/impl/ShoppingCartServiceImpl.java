package com.nico.store.store.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.repository.ShoppingCartRepository;
import com.nico.store.store.service.CartItemService;
import com.nico.store.store.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem cartItem : cartItemList) {
			if(cartItem.getArticle().getStock() > 0) {
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubtotal());
			}
		}			
		shoppingCart.setGrandTotal(cartTotal);
		shoppingCartRepository.save(shoppingCart);
		return shoppingCart;
	}

	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem item : cartItems) {
			item.setShoppingCart(null);
			cartItemService.save(item);			
		}
		shoppingCart.setGrandTotal(new BigDecimal(0));
		shoppingCartRepository.save(shoppingCart);
	}
	

}
