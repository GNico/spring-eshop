package com.nico.store.store.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nico.store.store.domain.Article;
import com.nico.store.store.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/add")
	public String addBook(Model model) {
		Article article = new Article();
		model.addAttribute("article", article);
		return "addArticle";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addBookPost(@ModelAttribute("article") Article article, HttpServletRequest request) {
		articleService.save(article);
		MultipartFile articleImage = article.getImage();
		try {
			byte[] bytes = articleImage.getBytes();
			String name = article.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/article/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:article-list";
	}
	
	@RequestMapping("/article-list")
	public String articleList(Model model) {
		List<Article> articles = articleService.findAll();
		model.addAttribute("articles", articles);
		return "articleList";
	}
	
	@RequestMapping("/edit")
	public String editArticle(@RequestParam("id") Long id, Model model) {
		Article article = articleService.findById(id);
		model.addAttribute("article", article);
		return "editArticle";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request) {
		articleService.save(article);
		MultipartFile articleImage = article.getImage();
		if (!articleImage.isEmpty()) {
			try {
				byte[] bytes = articleImage.getBytes();
				String name = article.getId() + ".png";
				Files.delete(Paths.get("src/main/resources/static/image/article/"+name));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/article/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:article-list";
	}
	
}
