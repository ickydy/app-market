package org.edupoll.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.edupoll.market.model.Product;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({ "/", "/index" })
@RequiredArgsConstructor
public class IndexController {

	private final ProductDao productDao;

	@GetMapping
	public String showIndex(@RequestParam(defaultValue = "1") int p, @RequestParam(defaultValue = "") String search,
			Model model) {

		Map<String, Object> criteria = new HashMap<>();
		criteria.put("start", (p - 1) * 6 + 1);
		criteria.put("end", p * 6);

		String[] words = search.split("\\s+");
		criteria.put("words", words);

		List<Product> products = productDao.findSomeByPaging(criteria);
		model.addAttribute("products", products);

		int count = productDao.countProducts(criteria);
		if (count >= 6 && count % 6 == 0) {
			model.addAttribute("pages", count / 6);
		} else if (count < 6) {
			model.addAttribute("pages", 1);
		} else {
			model.addAttribute("pages", count / 6 + 1);
		}

		model.addAttribute("currentPage", p);

		return "menu/index";
	}
}
