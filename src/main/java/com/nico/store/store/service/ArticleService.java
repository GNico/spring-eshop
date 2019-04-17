package com.nico.store.store.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nico.store.store.domain.Article;

public interface ArticleService {

	public static final String[] ALL_SIZES = { "38", "39", "40", "41", "42", "43", "44", "45", "46" };

	Article save(Article article);
	
	List<Article> findAll();
	
	Article findById(Long id);

	void deleteById(Long id);
	
	List<String> findAllSizes();

	List<String> findAllCategories();

	List<String> findAllBrands();

	List<Article> findByCriteria(Pageable pageable, String priceLow, String priceHigh, List<String> sizes, List<String> categories, List<String> brands);
}
