package com.andrew.proshop.service.product.converters;

import com.andrew.proshop.converter.AbstractConverter;
import com.andrew.proshop.dto.product.ProductDto;
import com.andrew.proshop.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductDtoConverter extends AbstractConverter<Product, ProductDto> {

    @Override
    protected ProductDto generateTarget() {
        return new ProductDto();
    }

    @Override
    public void convert(Product source, ProductDto target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setEAN(source.getEAN());
        target.setDescriptions(source.getDescriptions());
        target.setImage(source.getImage());
    }
}
