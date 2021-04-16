package com.andrew.proshop.service.product;


import com.andrew.proshop.dto.FileWrapperDto;
import com.andrew.proshop.dto.product.ProductDto;
import com.andrew.proshop.exception.EntityCouldNotBeFoundException;
import com.andrew.proshop.model.Check;
import com.andrew.proshop.model.Product;
import com.andrew.proshop.repository.CheckRepository;
import com.andrew.proshop.repository.ProductRepository;
import com.andrew.proshop.service.product.converters.ProductToProductDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductToProductDtoConverter productToProductDtoConverter;

    @Override
    public ProductDto createProduct(ProductDto newProduct) {
        newProduct.setId(null);
        Product product = new Product();
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public Product getProduct(Long id) {
        return getProductById(id);
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto updateProduct) {
        Product product = getProduct(id);
        product.setName(updateProduct.getName());
        product.setEAN(updateProduct.getEAN());
        product.setPrice(updateProduct.getPrice());
        product.setDescriptions(updateProduct.getDescriptions());
        productRepository.save(product);
        return convertToDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void addImage(Long id, byte[] image) {
        Product product = getProduct(id);
        product.setImage(image);
        productRepository.save(product);
    }

    @Override
    public void deleteImage(Long id) {
        Product product = getProduct(id);
        product.setImage(null);
        productRepository.save(product);
    }

    private ProductDto convertToDto(Product product) {
        return productToProductDtoConverter.convert(product);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("Product with id %d could not found", id)));
    }
}
