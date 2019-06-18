package com.nico.store.store.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int qty;
	private String size;
	
	@OneToOne
	@JoinColumn(name="article_id")
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	public CartItem() {		
	}
	
	public boolean canUpdateQty(Integer qty) {
		return qty == null || qty <= 0 || this.getArticle().hasStock(qty);
	}
	
	public BigDecimal getSubtotal() {
		return new BigDecimal(article.getPrice()).multiply(new BigDecimal(qty));
	}

	public void addQuantity(int qty) {
		if (qty > 0) {
			this.qty = this.qty + qty;
		}
	}
	
	public boolean hasSameSizeThan(String size2) {
		return this.size.equals(size2);
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

	
	
	
}