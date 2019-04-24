package com.nico.store.store.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.ArticleService;
import com.nico.store.store.service.CartItemService;
import com.nico.store.store.service.ShoppingCartService;
import com.nico.store.store.service.UserService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);		
		shoppingCartService.updateShoppingCart(shoppingCart);		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);		
		return "shoppingCart";
	}

	@RequestMapping("/add-item")
	public String addItem(
			@ModelAttribute("article") Article article,
			@RequestParam("qty") String qty,
			@RequestParam("size") String size,
			Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		article = articleService.findById(article.getId());		
		if (Integer.parseInt(qty) > article.getStock()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/article-detail?id="+article.getId();
		}		
		cartItemService.addArticleToCartItem(article, user, Integer.parseInt(qty), size);
		model.addAttribute("addArticleSuccess", true);		
		return "forward:/article-detail?id="+article.getId();
	}
	
	@RequestMapping("/update-item")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);		
		return "redirect:/shopping-cart/cart";
	}
	
	@RequestMapping("/remove-item")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id));		
		return "redirect:/shopping-cart/cart";
	} 
}
