package com.nico.store.store.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.nico.store.store.domain.Brand;
import com.nico.store.store.domain.Category;
import com.nico.store.store.domain.Size;
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
		model.addAttribute("allSizes", articleService.findAllSizes());
		model.addAttribute("allBrands", articleService.findAllBrands());
		model.addAttribute("allCategories", articleService.findAllCategories());
		return "addArticle";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addBookPost(@ModelAttribute("article") Article article, HttpServletRequest request) {
		String paramSizes = request.getParameter("size");
		List<String> sizeValues = Arrays.asList(paramSizes.split("\\s*,\\s*"));
		article.setSizes(new ArrayList<Size>());
		for (String sizeVal : sizeValues) {
			Size size = new Size();
			size.setValue(sizeVal);
			size.setArticle(article);
			article.getSizes().add(size);
		}
		
		String paramCategories = request.getParameter("category");
		List<String> categoriesValues = Arrays.asList(paramCategories.split("\\s*,\\s*"));
		article.setCategories(new ArrayList<Category>());
		for (String catVal : categoriesValues) {
			Category cat = new Category();
			cat.setName(catVal);
			cat.setArticle(article);
			article.getCategories().add(cat);
		}
		
		String paramBrands = request.getParameter("brand");
		List<String> brandsValues = Arrays.asList(paramBrands.split("\\s*,\\s*"));
		article.setBrands(new ArrayList<Brand>());
		for (String brandVal : brandsValues) {
			Brand brand = new Brand();
			brand.setName(brandVal);
			brand.setArticle(article);
			article.getBrands().add(brand);
		}
		
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
		String preselectedSizes = "";
		for (Size size : article.getSizes()) {
			preselectedSizes += (size.getValue() + ",");
		}
		String preselectedBrands = "";
		for (Brand brand : article.getBrands()) {
			preselectedBrands += (brand.getName() + ",");
		}
		String preselectedCategories = "";
		for (Category category : article.getCategories()) {
			preselectedCategories += (category.getName() + ",");
		}
		
		model.addAttribute("article", article);
		model.addAttribute("preselectedSizes", preselectedSizes);
		model.addAttribute("preselectedBrands", preselectedBrands);
		model.addAttribute("preselectedCategories", preselectedCategories);
		model.addAttribute("allSizes", articleService.findAllSizes());
		model.addAttribute("allBrands", articleService.findAllBrands());
		model.addAttribute("allCategories", articleService.findAllCategories());
		return "editArticle";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request) {
		String paramSizes = request.getParameter("size");
		List<String> sizeValues = Arrays.asList(paramSizes.split("\\s*,\\s*"));
		article.setSizes(new ArrayList<Size>());
		for (String sizeVal : sizeValues) {
			Size size = new Size();
			size.setValue(sizeVal);
			size.setArticle(article);
			article.getSizes().add(size);
		}
		
		String paramCategories = request.getParameter("category");
		List<String> categoriesValues = Arrays.asList(paramCategories.split("\\s*,\\s*"));
		article.setCategories(new ArrayList<Category>());
		for (String catVal : categoriesValues) {
			Category cat = new Category();
			cat.setName(catVal);
			cat.setArticle(article);
			article.getCategories().add(cat);
		}
		
		String paramBrands = request.getParameter("brand");
		List<String> brandsValues = Arrays.asList(paramBrands.split("\\s*,\\s*"));
		article.setBrands(new ArrayList<Brand>());
		for (String brandVal : brandsValues) {
			Brand brand = new Brand();
			brand.setName(brandVal);
			brand.setArticle(article);
			article.getBrands().add(brand);
		}
		
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
	
	@RequestMapping("/delete")
	public String deleteArticle(@RequestParam("id") Long id) {
		articleService.deleteById(id);
		try {
			String name = id + ".png";
			Files.delete(Paths.get("src/main/resources/static/image/article/"+name));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:article-list";

	}
	
}
