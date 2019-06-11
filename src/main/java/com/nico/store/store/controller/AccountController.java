package com.nico.store.store.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nico.store.store.domain.Address;
import com.nico.store.store.domain.Order;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.OrderService;
import com.nico.store.store.service.UserService;
import com.nico.store.store.service.impl.UserSecurityService;

import utility.SecurityUtility;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private OrderService orderService;
		
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Authentication authentication) {				
		User user = (User) authentication.getPrincipal();
		//User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myProfile";
	}
	
	@RequestMapping("/myOrders")
	public String myOrders(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		List<Order> orders = orderService.findByUser(user);
		model.addAttribute("orders", orders);
		return "myOrders";
	}
	
	@RequestMapping("/myAddress")
	public String myAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myAddress";
	}
	
	@RequestMapping(value="/update-user-address", method=RequestMethod.POST)
	public String updateUserAddress(@ModelAttribute("address") Address address, 
			Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		currentUser.setAddress(address);
		userService.save(currentUser);
		return "redirect:myAddress";
	}
	
	@RequestMapping(value="/new-user", method=RequestMethod.POST)
	public String newUserPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResults,
							  @ModelAttribute("new-password") String password, Model model) {
		model.addAttribute("email", user.getEmail());
		model.addAttribute("username", user.getUsername());		
		if (bindingResults.hasErrors()) {
			return "myAccount";
		}		
		boolean invalidFields = false;
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExists", true);
			invalidFields = true;
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);
			invalidFields = true;
		}	
		if (invalidFields) {
			return "myAccount";
		}		
		userService.createUser(user.getUsername(), user.getEmail(), password, Arrays.asList("ROLE_USER"));	
		userSecurityService.authenticateUser(user.getUsername());
		model.addAttribute("user", user);
		return "myProfile";  
	}
		
	@RequestMapping(value="/update-user-info", method=RequestMethod.POST)
	public String updateUserInfo( @ModelAttribute("user") User user,
								  @RequestParam("newPassword") String newPassword,
								  Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		/*check username already exists*/
		User existingUser = userService.findByUsername(user.getUsername());
		if (existingUser != null && !existingUser.getId().equals(currentUser.getId()))  {
			model.addAttribute("usernameExists", true);
			return "myProfile";
		}	
		/*check email already exists*/
		existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && !existingUser.getId().equals(currentUser.getId()))  {
			model.addAttribute("emailExists", true);
			return "myProfile";
		}			
		/*update password*/
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				return "myProfile";
			}
		}		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());		
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getUsername());		
		return "myProfile";
	}
	
	@RequestMapping("/order-detail")
	public String orderDetail(@RequestParam("order") Long id, Model model) {
		Order order = orderService.findById(id);
		model.addAttribute("order", order);
		return "orderDetails";
	}
	
}
