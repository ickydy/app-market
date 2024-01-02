package org.edupoll.market.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	private Integer id;
	private String title;
	private String type;
	private Integer price;
	private String description;
	private String accountId;
	private Integer viewCnt;

	private Account account;

	private List<ProductImage> images;
}
