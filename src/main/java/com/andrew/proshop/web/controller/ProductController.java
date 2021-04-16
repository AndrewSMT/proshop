package com.andrew.proshop.web.controller;

import com.andrew.proshop.dto.product.ProductDto;
import com.andrew.proshop.service.product.ProductService;
import com.andrew.proshop.service.product.pdf.CheckPdfGeneratorImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private CheckPdfGeneratorImpl checkPdfGenerator;
    private ServletContext servletContext;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getProductList() {
        return ResponseEntity.ok(productService.getProductList());
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto newProduct) {
        ProductDto user = productService.createProduct(newProduct);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto updateProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updateProduct));
    }

    @GetMapping(value = "/{id}/pdf-report")
    public ResponseEntity<byte[]> printPdfReport(@PathVariable Long id, HttpServletResponse response) throws IOException {
        checkPdfGenerator.generateCheckPdf(response, id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> addImage(@PathVariable Long id, HttpEntity<byte[]> imageBytes) {
        productService.addImage(id, imageBytes.getBody());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/image/")
    public void deleteImage(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
