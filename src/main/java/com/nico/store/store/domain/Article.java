package com.nico.store.store.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int stock;	
	private double price;
	private String picture;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Size> sizes;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Brand> brands;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Category> categories;

	public Article() {
	}
	
	public boolean hasStock(int amount) {
		return (this.getStock() > 0) && (amount <= this.getStock());
	}
	
	public void decreaseStock(int amount) {
		this.stock -= amount;
	}
	
	public void addSize(Size size) {
        sizes.add(size);
        size.setArticle(this);
    }
 
    public void removeSize(Size size) {
        sizes.remove(size);
        size.setArticle(null);
    }
    
	public void addCategory(Category category) {
        categories.add(category);
        category.setArticle(this);
    }
 
    public void removeCategory(Category category) {
    	categories.remove(category);
        category.setArticle(null);
    }
    
	public void addSize(Brand brand) {
        brands.add(brand);
        brand.setArticle(this);
    }
 
    public void removeSize(Brand brand) {
    	brands.remove(brand);
        brand.setArticle(null);
    }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public List<Size> getSizes() {
		Collections.sort(sizes);
		return sizes;
	}
	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}
	public List<Brand> getBrands() {
		return brands;
	}
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	

}
