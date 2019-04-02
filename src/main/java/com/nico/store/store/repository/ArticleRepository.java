package com.nico.store.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
	
	

}
