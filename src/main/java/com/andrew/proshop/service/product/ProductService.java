package com.andrew.proshop.service.product;


import com.andrew.proshop.dto.product.ProductDto;
import com.andrew.proshop.model.Product;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto newProduct);

    Product getProduct(Long id);

    List<Product> getProductList();

    ProductDto updateProduct(Long id, ProductDto updateProduct);

    void deleteProduct(Long id);

    void addImage(Long id, byte[] image);

    void deleteImage(Long id);
}
