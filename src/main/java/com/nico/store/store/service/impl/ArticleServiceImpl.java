package com.nico.store.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.Size;
import com.nico.store.store.repository.ArticleRepository;
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
	public Article findById(Long id) {
		Optional<Article> opt = articleRepository.findById(id);
		return opt.get();
	}

	@Override
	public void deleteById(Long id) {
		articleRepository.deleteById(id);
		
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

	@Override
	public List<Article> findByCriteria(String priceLow, String priceHigh, String size) {
		return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (size!=null && !size.isEmpty()) {
                	Join<Article, Size> joinSize = root.join("sizes");
                	predicates.add(criteriaBuilder.and(criteriaBuilder.equal(joinSize.get("value"), size)));
                }
                
                if (priceLow!=null && !priceLow.isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("price"), priceLow)));
                }
                if (priceHigh!=null && !priceHigh.isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThan(root.get("price"), priceHigh)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
	}


}
