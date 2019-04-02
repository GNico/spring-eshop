package com.nico.store.store.service;

import java.util.List;

import com.nico.store.store.domain.Article;

public interface ArticleService {

	Article save(Article article);
	
	List<Article> findAll();
	
	Article findById(Long id);
}
