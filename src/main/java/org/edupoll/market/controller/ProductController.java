package org.edupoll.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {

	@GetMapping("/register")
	public String showProductRegister(Model model) {
		return "product/register";
	}
	
	@PostMapping("/register")
	public String proceedRegister(@RequestParam MultipartFile[] image) {
		System.out.println(image.length);
		
		return null;
	}
}
