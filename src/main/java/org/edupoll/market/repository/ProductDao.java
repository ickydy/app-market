package org.edupoll.market.repository;

import org.edupoll.market.model.Product;
import org.edupoll.market.model.ProductImage;

public interface ProductDao {
	public int save(Product product);

	public int saveImage(ProductImage productImage);
	
	public Product findById(int productId);
}
