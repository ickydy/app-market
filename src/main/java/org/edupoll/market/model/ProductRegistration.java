package org.edupoll.market.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductRegistration {
	private MultipartFile[] images;
	private String title;
	private String type;
	private Integer price;
	private String description;
}
