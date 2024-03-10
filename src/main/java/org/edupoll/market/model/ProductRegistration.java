package org.edupoll.market.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRegistration {
	private MultipartFile[] images;
	private String title;
	private String type;
	private Integer price;
	private String description;
}
