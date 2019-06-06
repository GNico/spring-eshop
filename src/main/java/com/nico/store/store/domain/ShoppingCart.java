package com.nico.store.store.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<CartItem> cartItems;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public ShoppingCart() {
	}
	
	public BigDecimal getGrandTotal() {
		BigDecimal cartTotal = new BigDecimal(0);
		for (CartItem item : this.cartItems) {
			cartTotal = cartTotal.add(item.getSubtotal());
		}
		return cartTotal;
	}
	
	public void removeCartItem(CartItem cartItem) {
		cartItems.removeIf(item -> item.getId() == cartItem.getId());
	}
	
	public void clearItems() {
		cartItems.clear();
	}
	
	public CartItem findCartItemByArticle(Long id) {
		for (CartItem item : this.cartItems) {
			if (item.getArticle().getId() == id) {
				return item;
			}
		}
		return null;
	}
	
	public int getItemCount() {
		return this.cartItems.size();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
