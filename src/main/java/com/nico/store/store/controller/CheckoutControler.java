package com.nico.store.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nico.store.store.domain.Address;
import com.nico.store.store.domain.Order;
import com.nico.store.store.domain.Payment;
import com.nico.store.store.domain.Shipping;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.OrderService;
import com.nico.store.store.service.ShoppingCartService;

@Controller
public class CheckoutControler {
	

	@Autowired 
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;


	@RequestMapping("/checkout")
	public String checkout( @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
							Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();	
		System.out.println("getting shop cart");
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
		System.out.println("finish getting shop cart");
		if(shoppingCart.isEmpty()) {
			model.addAttribute("emptyCart", true);
			return "redirect:/shopping-cart/cart";
		}						
		model.addAttribute("cartItemList", shoppingCart.getCartItems());
		model.addAttribute("shoppingCart", shoppingCart);
		if(missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}		
		return "checkout";
		
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
							@ModelAttribute("address") Address address,
							@ModelAttribute("payment") Payment payment,
							Model model, Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();		
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);				
		shipping.setAddress(address);
		Order order = orderService.createOrder(shoppingCart, shipping, payment, user);		
		
		//shoppingCartService.clearShoppingCart(shoppingCart);
		model.addAttribute("order", order);	
		return "orderSubmitted";
	}

}
