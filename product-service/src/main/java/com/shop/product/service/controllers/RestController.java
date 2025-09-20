package com.shop.product.service.controllers;

import com.shop.product.service.product.entity.Product;
import com.shop.product.service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class RestController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.orElse(null);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

     @GetMapping("/get")
    public List <Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
