package com.nico.store.store.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.Order;
import com.nico.store.store.domain.Payment;
import com.nico.store.store.domain.Shipping;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.repository.OrderRepository;
import com.nico.store.store.service.CartItemService;
import com.nico.store.store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartItemService cartItemService;
	
	@Override
	public synchronized Order createOrder(ShoppingCart shoppingCart, Shipping shipping, Payment payment, User user) {
		Order order = new Order();
		order.setUser(user);
		order.setPayment(payment);
		order.setShipping(shipping);
		
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem item : cartItems) {
			Article article = item.getArticle();
			item.setOrder(order);
			article.setStock(article.getStock() - item.getQty());		
		}
		order.setCartItems(cartItems);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shipping.setOrder(order);
		payment.setOrder(order);
		
		return orderRepository.save(order);
		
	}
	
	public Order findById(Long id) {
		Optional<Order> opt = orderRepository.findById(id);
		return opt.get();
	}	

}
