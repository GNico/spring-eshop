package com.nico.store.store.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Article {
	
	public static final String[] ALL_SIZES = { "38", "39", "40", "41", "42", "43", "44", "45", "46" };
	public static final String[] ALL_BRANDS = { "Nike", "Adidas", "Puma", "Dior", "New Balance", "Brooks" };
	public static final String[] ALL_CATEGORIES = { "Deportivas", "Urbanas" };
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private int stock;	
	private double price;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Size> sizes;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Brand> brands;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Category> categories;

	@Transient
	private MultipartFile image;
	
	public Article() {
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
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public List<Size> getSizes() {
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



	
	
	

}
