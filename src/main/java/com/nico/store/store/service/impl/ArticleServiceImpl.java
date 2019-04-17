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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.Brand;
import com.nico.store.store.domain.Category;
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
	public List<Article> findByCriteria(Pageable pageable, String priceLow, String priceHigh, List<String> sizes, List<String> categories, List<String> brands) {
		Page page = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();                
                query.distinct(true);                
                if (sizes!=null && !sizes.isEmpty()) {
                	Join<Article, Size> joinSize = root.join("sizes");
                	predicates.add(criteriaBuilder.and(joinSize.get("value").in(sizes)));
                }
                if (categories!=null && !categories.isEmpty()) {
                	Join<Article, Category> joinSize = root.join("categories");
                	predicates.add(criteriaBuilder.and(joinSize.get("name").in(categories)));
                }   
                if (brands!=null && !brands.isEmpty()) {
                	Join<Article, Brand> joinSize = root.join("brands");
                	predicates.add(criteriaBuilder.and(joinSize.get("name").in(brands)));
                }   
                if (priceLow!=null && !priceLow.isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("price"), priceLow)));
                }
                if (priceHigh!=null && !priceHigh.isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThan(root.get("price"), priceHigh)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
		
		page.getTotalElements();        // get total elements
        page.getTotalPages();           // get total pages
        return page.getContent();       // get List of Employee
		
		
		
	}


}
