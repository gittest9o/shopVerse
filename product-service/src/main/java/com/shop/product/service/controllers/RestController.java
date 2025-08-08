package com.shop.product.service.controllers;

import com.shop.product.service.product.Product;
import com.shop.product.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
}
