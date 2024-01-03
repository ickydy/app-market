package org.edupoll.market.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.Pick;
import org.edupoll.market.model.Product;
import org.edupoll.market.model.ProductImage;
import org.edupoll.market.model.ProductRegistration;
import org.edupoll.market.repository.PickDao;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductDao productDao;
	private final PickDao pickDao;

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
				.accountId(logonAccount.getId()) //
				.build();

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
						.productId(product.getId()) //
						.build();
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

	@PostMapping("/pick")
	public String proceedAddPick(@RequestParam int targetProductId, @SessionAttribute Account logonAccount) {
		Pick one = Pick.builder() //
				.targetProductId(targetProductId) //
				.ownerAccountId(logonAccount.getId()) //
				.build();
		int count = pickDao.countByOwnerAndTarget(one);
		if (count == 0) {
			pickDao.save(one);
		}
		return "redirect:/product/" + targetProductId;
	}
	
	@DeleteMapping("/pick")
	public String proceedRemovePick(@RequestParam int targetProductId, @SessionAttribute Account logonAccount) {
		Pick one = Pick.builder() //
				.targetProductId(targetProductId) //
				.ownerAccountId(logonAccount.getId()) //
				.build();
		
		pickDao.deleteByOwnderAndTarget(one);
		
		return "redirect:/product/" + targetProductId;
	}
	
	@PostMapping("/pickAjax")
	@ResponseBody
	public String proceedAjaxAddPick(@RequestParam int targetProductId, @SessionAttribute Account logonAccount) {
		Pick one = Pick.builder() //
				.targetProductId(targetProductId) //
				.ownerAccountId(logonAccount.getId()) //
				.build();
		int count = pickDao.countByOwnerAndTarget(one);
		if (count == 0) {
			pickDao.save(one);
		}
		return "success";
	}
	
	@DeleteMapping("/pickAjax")
	@ResponseBody
	public String proceedAjaxRemovePick(@RequestParam int targetProductId, @SessionAttribute Account logonAccount) {
		Pick one = Pick.builder() //
				.targetProductId(targetProductId) //
				.ownerAccountId(logonAccount.getId()) //
				.build();
		
		pickDao.deleteByOwnderAndTarget(one);
		
		return "success";
	}

	@GetMapping("/{productId}")
	public String showProductDeatil(@PathVariable int productId,
			@SessionAttribute(required = false) Account logonAccount, Model model) {
		Product found = productDao.findById(productId);
		int totalPick = pickDao.countByTarget(productId);

		if (logonAccount == null) {
			model.addAttribute("picked", false);
		} else {
			Pick one = Pick.builder() //
					.targetProductId(productId) //
					.ownerAccountId(logonAccount.getId()) //
					.build();
			int count = pickDao.countByOwnerAndTarget(one);
			model.addAttribute("picked", count == 1 ? true : false);
		}

		model.addAttribute("product", found);
		model.addAttribute("totalPick", totalPick);

		return "product/view";
	}
	
	@GetMapping("/mypick")
	public String showMyPick(@SessionAttribute Account logonAccount, Model model) {
		List<Pick> picks = pickDao.findByOwner(logonAccount.getId());
		List<Product> products = new ArrayList<>();
		Map<Integer, Integer> totalPicks = new LinkedHashMap<Integer, Integer>();
		System.out.println(picks.size());
		if (picks != null) {
			for(Pick pick : picks) {
				Product product = productDao.findById(pick.getTargetProductId());
				products.add(product);
				int totalPick = pickDao.countByTarget(product.getId());
				totalPicks.put(product.getId(), totalPick);
			}
		}
		model.addAttribute("products", products);
		return "product/mypick";
	}
}
