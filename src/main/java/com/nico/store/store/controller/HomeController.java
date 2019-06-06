package com.nico.store.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nico.store.store.domain.Article;
import com.nico.store.store.service.ArticleService;

@Controller
public class HomeController {
		
	@Autowired
	private ArticleService articleService;
	
	
	@RequestMapping("/")
	public String index(Model model) {		
		List<Article> articles = articleService.findAllTop(12);
		model.addAttribute("articles", articles);
		return "index";
	}
	
	@RequestMapping("/login")
	public String log(Model model) {
		return "myAccount";
	}
	
}
