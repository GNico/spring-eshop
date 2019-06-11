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
		System.out.println("start global advice");
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
			!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) ) {				
			User user =  (User) SecurityContextHolder.getContext()
			           .getAuthentication().getPrincipal(); 
			
			System.out.println(user.toString());

			//User sameuser = userService.findByUsername(user.getUsername()); 
			//if (sameuser != null) {
				//ShoppingCart shopCart = user.getShoppingCart();  
				model.addAttribute("shoppingCartItemNumber", 0 );
			//}
		} else { 
			model.addAttribute("shoppingCartItemNumber", 0);
		} 
		System.out.println("end global advice");
	}
}
