package com.nico.store.store.controller;

import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nico.store.store.domain.Article;
import com.nico.store.store.form.ArticleFilterForm;
import com.nico.store.store.service.ArticleService;
import com.nico.store.store.type.SortFilter;

@Controller
public class StoreController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/store")
	public String store(@ModelAttribute("filters") ArticleFilterForm filters, Model model) {
		Integer page = filters.getPage();			
		int pagenumber = (page == null ||  page <= 0) ? 0 : page-1;
		SortFilter sortFilter = new SortFilter(filters.getSort());	
		Page<Article> pageresult = articleService.findByCriteria(PageRequest.of(pagenumber,9, sortFilter.getSortType()), 
																filters.getPricelow(), filters.getPricehigh(), 
																filters.getSize(), filters.getCategory(), filters.getBrand(), filters.getSearch());	
		model.addAttribute("allCategories", articleService.findAllCategories());
		model.addAttribute("allBrands", articleService.findAllBrands());
		model.addAttribute("allSizes", articleService.findAllSizes());
		model.addAttribute("articles", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		return "store";
	}
	
	
	@RequestMapping("/article-detail")
	public String articleDetail(@PathParam("id") Long id, Model model) {
		Article article = articleService.findById(id);
		model.addAttribute("article", article);
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);		
		model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
		model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
		return "articleDetail";
	}
	

}
