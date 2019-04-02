package com.nico.store.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.store.store.domain.Article;
import com.nico.store.store.repository.ArticleRepository;
import com.nico.store.store.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Article save(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public List<Article> findAll() {
		return (List<Article>) articleRepository.findAll();
	}

	@Override
	public Article findById(Long id) {
		Optional<Article> opt = articleRepository.findById(id);
		return opt.get();
	}

}
