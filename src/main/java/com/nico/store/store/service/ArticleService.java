package com.nico.store.store.service;

import java.util.List;

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

}
