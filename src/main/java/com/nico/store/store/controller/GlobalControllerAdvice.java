package com.nico.store.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	
	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	@ModelAttribute
	public void populateModel(Model model) {	
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
			!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ) {				
			UserDetails userDetails =  (UserDetails) SecurityContextHolder.getContext()
			           .getAuthentication().getPrincipal(); 			
			User user = userService.findByUsername(userDetails.getUsername());
			if (user != null) {
				ShoppingCart shopCart = user.getShoppingCart();
				model.addAttribute("shoppingCartItemNumber", shopCart.getItemCount() );
			}
		}
	}
}
