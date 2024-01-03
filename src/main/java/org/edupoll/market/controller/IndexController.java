package org.edupoll.market.controller;

import java.util.List;

import org.edupoll.market.model.Product;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({"/","/index"})
@RequiredArgsConstructor
public class IndexController {

	private final ProductDao productDao;
	
	@GetMapping
	public String showIndex(Model model) {
		
		List<Product> products = productDao.findAllOrderByIdDesc();
		model.addAttribute("products", products);
		
		return "menu/index";
	}
}
