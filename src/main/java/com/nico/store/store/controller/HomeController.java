package com.nico.store.store.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.User;
import com.nico.store.store.domain.security.Role;
import com.nico.store.store.domain.security.UserRole;
import com.nico.store.store.service.ArticleService;
import com.nico.store.store.service.UserService;
import com.nico.store.store.service.impl.UserSecurityService;

import utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String log(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/forgotPassword")
	public String forgotPassword( Model model) {
		model.addAttribute("classActiveForgotPassword", true);
		return "myAccount";
	}
	
	@RequestMapping(value="/new-user", method=RequestMethod.POST)
	public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, @ModelAttribute("new-password") String password, Model model) throws Exception {
		
		//model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			return "myAccount";
		}
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			return "myAccount";
		}
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user,role));
		try {
			userService.createUser(user,userRoles);
		} catch (Exception e) {
			return "myAccount";			
		}

		//set new user as authenticated
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), 
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit", true);
		return "myProfile";  

	}
	
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}
	
	@RequestMapping("/myOrders")
	public String myOrders(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myOrders";
	}
	
	@RequestMapping("/myAddress")
	public String myAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myAddress";
	}
	
	
	@RequestMapping(value="/update-user-info", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
			) throws Exception {
		User currentUser = userService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		/*check email already exists*/
		if (userService.findByEmail(user.getEmail())!=null) {
			if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		
		/*check username already exists*/
		if (userService.findByUsername(user.getUsername())!=null) {
			if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		
//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				return "myProfile";
			}
		}
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		userService.save(currentUser);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "myProfile";
	}
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "adminHome";
	}
	
	@RequestMapping("/store")
	public String store(HttpServletRequest request, Model model) {
		String sort = request.getParameter("sort");
		String[] sizes = request.getParameterValues("size");
		String[] categories = request.getParameterValues("category");
		String[] brands = request.getParameterValues("brand");
		String priceLow = request.getParameter("pricelow");
		String priceHigh= request.getParameter("pricehigh");
		
		String page = request.getParameter("page");
		int pagenumber = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(request.getParameter("page"));
		
		List<String> filterSizes = (sizes != null) ? Arrays.asList(sizes) : null;
		List<String> filterCategories = (categories != null) ? Arrays.asList(categories) : null;
		List<String> filterBrands = (brands != null) ? Arrays.asList(brands) : null;
		
		Page<Article> pageresult = articleService.findByCriteria(PageRequest.of(pagenumber-1,9), priceLow, priceHigh, filterSizes, filterCategories, filterBrands );	
		
		List<Article> articles = pageresult.getContent();
		Long totalitems = pageresult.getTotalElements();
		int itemsperpage = 9;
		
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.findAllCategories());
		model.addAttribute("allBrands", articleService.findAllBrands());
		model.addAttribute("allSizes", articleService.findAllSizes());
		model.addAttribute("sort", sort);
		model.addAttribute("page", page);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("itemsperpage", itemsperpage);
		return "store";
	}
	
	@RequestMapping("/article-detail")
	public String articleDetail(@PathParam("id") Long id, Model model) {
		Article article = articleService.findById(id);
		model.addAttribute("article", article);
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		return "articleDetail";
		
	}
	
}
