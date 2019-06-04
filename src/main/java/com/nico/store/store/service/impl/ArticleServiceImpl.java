package com.nico.store.store.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nico.store.store.domain.Article;
import com.nico.store.store.repository.ArticleRepository;
import com.nico.store.store.repository.ArticleSpecification;
import com.nico.store.store.service.ArticleService;

@Service
@Transactional
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
	public List<Article> findAllTop(int number) {
		return articleRepository.findAll(PageRequest.of(0,number)).getContent(); 
	}

	@Override
	public Article findById(Long id) {
		Optional<Article> opt = articleRepository.findById(id);
		return opt.get();
	}

	@Override
	public void deleteById(Long id) {
		articleRepository.deleteById(id);		
	}

	@Override
	public Page<Article> findByCriteria(Pageable pageable, Integer priceLow, Integer priceHigh, 
										List<String> sizes, List<String> categories, List<String> brands, String search) {		
		Page<Article> page = articleRepository.findAll(ArticleSpecification.filterBy(priceLow, priceHigh, sizes, categories, brands, search), pageable);
        return page;		
	}
	
	@Override
	public List<String> findAllSizes() {
		return articleRepository.findAllSizes();
	}

	@Override
	public List<String> findAllCategories() {
		return articleRepository.findAllCategories();
	}

	@Override
	public List<String> findAllBrands() {
		return articleRepository.findAllBrands();
	}
}
