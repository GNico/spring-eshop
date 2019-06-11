package com.nico.store.store.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.CartItem;
import com.nico.store.store.domain.ShoppingCart;
import com.nico.store.store.domain.User;
import com.nico.store.store.service.ArticleService;
import com.nico.store.store.service.ShoppingCartService;
import com.nico.store.store.service.UserService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;
		
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {		
		User user = userService.findByUsername(principal.getName());		
		ShoppingCart shoppingCart = user.getShoppingCart();				
		List<CartItem> cartItemList = shoppingCart.getCartItems();	
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);		
		return "shoppingCart";
	}

	@RequestMapping("/add-item")
	public String addItem(@ModelAttribute("article") Article article, @RequestParam("qty") String qty,
						@RequestParam("size") String size, RedirectAttributes attributes, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		article = articleService.findArticleById(article.getId());		
		if (!article.hasStock(Integer.parseInt(qty))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/article-detail?id="+article.getId();
		}		
		shoppingCartService.addArticleToCartItem(article, user.getShoppingCart(), Integer.parseInt(qty), size);
		attributes.addFlashAttribute("addArticleSuccess", true);
		return "redirect:/article-detail?id="+article.getId();
	}
	
	@RequestMapping("/update-item")
	public String updateItemQuantity(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty) {
		CartItem cartItem = shoppingCartService.findById(cartItemId);
		cartItem.setQty(qty);
		shoppingCartService.save(cartItem);		
		return "redirect:/shopping-cart/cart";
	}
	
	@RequestMapping("/remove-item")
	public String removeItem(@RequestParam("id") Long id, Principal principal) {		
		User user = userService.findByUsername(principal.getName());		
		shoppingCartService.removeCartItem(user.getShoppingCart(), shoppingCartService.findById(id));		
		return "redirect:/shopping-cart/cart";
	} 
}
