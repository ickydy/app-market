package org.edupoll.market.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.Product;
import org.edupoll.market.repository.ProductDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

	private final ProductDao productDao;

	@PostMapping("/product/sold")
	public Object proceedSoldProduct(@SessionAttribute Account logonAccount, @RequestParam Integer productId) {
		Map<String, Object> response = new LinkedHashMap<>();

		Product product = productDao.findById(productId);
		if (product == null) {
			response.put("result", false);
			response.put("cause", "존재하지 않는 상품입니다.");
			return response;
		}
		if (!product.getAccount().equals(logonAccount)) {
			response.put("result", false);
			response.put("cause", "권한이 없습니다.");
			return response;
		}
		
		product.setType("sold");
		productDao.updateType(product);
		response.put("result", true);
		
		return response;
	}

}