package com.nico.store.store.form;

import java.util.List;

public class ArticleFilterForm {
	
	private List<String> size;
	private List<String> category;
	private List<String> brand;
	private Integer pricelow;
	private Integer pricehigh;
	private String sort;
	private Integer page;
	private String search;
	
	public ArticleFilterForm() {
	}

	public List<String> getSize() {
		return size;
	}

	public void setSize(List<String> size) {
		this.size = size;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getBrand() {
		return brand;
	}

	public void setBrand(List<String> brand) {
		this.brand = brand;
	}

	public Integer getPricelow() {
		return pricelow;
	}

	public void setPricelow(Integer pricelow) {
		this.pricelow = pricelow;
	}

	public Integer getPricehigh() {
		return pricehigh;
	}

	public void setPricehigh(Integer pricehigh) {
		this.pricehigh = pricehigh;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	
	
}
