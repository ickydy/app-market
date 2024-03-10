package org.edupoll.market.repository;

import java.util.List;
import java.util.Map;

import org.edupoll.market.model.Product;
import org.edupoll.market.model.ProductImage;

public interface ProductDao {
	public int save(Product product);

	public int saveImage(ProductImage productImage);

	public Product findById(int productId);

	public List<Product> findAllOrderByIdDesc();
	
	public List<Product> findSomeByPaging(Map<String, Object> criteria);
	
	public int countProducts(Map<String, Object> criteria);

	public int updateViewCnt(Map<String, Object> criteria);
	
	public int updateType(Product product);
}
