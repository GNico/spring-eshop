package com.nico.store.store.service;

import com.nico.store.store.domain.Order;
import com.nico.store.store.domain.Payment;
import com.nico.store.store.domain.ShippingAddress;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, Payment payment, User user);
}
