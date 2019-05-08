package com.nico.store.store.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nico.store.store.domain.Address;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.Order;
import com.nico.store.store.domain.Payment;
import com.nico.store.store.domain.Shipping;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.CartItemService;
import com.nico.store.store.service.OrderService;
import com.nico.store.store.service.ShoppingCartService;
import com.nico.store.store.service.UserService;

@Controller
public class CheckoutControler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired 
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;


	@RequestMapping("/checkout")
	public String checkout( @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
							Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());		
		if(cartItemList.size() == 0) {
			model.addAttribute("emptyCart", true);
			return "redirect:/shopping-cart/cart";
		}		
		for (CartItem cartItem : cartItemList) {
			if(cartItem.getArticle().getStock() < cartItem.getQty()) {
				model.addAttribute("notEnoughStock", true);
				return "redirect:/shopping-cart/cart";
			}
		}			
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", user.getShoppingCart());
		if(missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}		
		return "checkout";
		
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
							@ModelAttribute("address") Address address,
							@ModelAttribute("payment") Payment payment,
							Principal principal, Model model) {
		
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		model.addAttribute("shoppingCart", shoppingCart);
		//	return "redirect:/checkout?missingRequiredField=true";	
		shipping.setAddress(address);
		Order order = orderService.createOrder(shoppingCart, shipping, payment, user);				
		shoppingCartService.clearShoppingCart(shoppingCart);
		model.addAttribute("order", order);	
		return "orderSubmitted";
	}

}
