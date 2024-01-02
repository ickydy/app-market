package org.edupoll.market.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.Product;
import org.edupoll.market.model.ProductImage;
import org.edupoll.market.model.ProductRegistration;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductDao productDao;

	@GetMapping("/register")
	public String showProductRegister(Model model) {
		return "product/register";
	}

	@PostMapping("/register")
	public String proceedRegister(@ModelAttribute ProductRegistration registration,
			@SessionAttribute Account logonAccount) throws IllegalStateException, IOException {
		System.out.println(registration);
		Product product = Product.builder() //
				.title(registration.getTitle()) //
				.type(registration.getType()) //
				.price(registration.getPrice()) //
				.description(registration.getDescription()) //
				.accountId(logonAccount.getId()).build();

		productDao.save(product);

		MultipartFile[] images = registration.getImages();
		List<ProductImage> productImages = new ArrayList<>();

		if (images.length > 0) {
			for (MultipartFile image : images) {
				if (image.isEmpty()) {
					continue;
				}
				String uuid = UUID.randomUUID().toString();
				ProductImage productImage = ProductImage.builder() //
						.url("/upload/productImage/" + product.getId() + "/" + uuid) //
						.path("d:\\upload\\productImage\\" + product.getId() + "\\" + uuid) //
						.productId(product.getId()).build();
				File dir = new File("d:\\upload\\productImage\\", String.valueOf(product.getId()));
				dir.mkdirs();
				File target = new File(dir, uuid);
				image.transferTo(target);

				productDao.saveImage(productImage);
				productImages.add(productImage);
			}
			product.setImages(productImages);
		}

		return "redirect:/product/" + product.getId();
	}
	
	@GetMapping("/{productId}")
	public String showProductDeatil(@PathVariable int productId, Model model) {
		Product found = productDao.findById(productId);
		
		model.addAttribute("product", found);
		
		return "product/view";
	}
}
