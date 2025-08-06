package com.shop.product.service.controllers;

import com.shop.product.service.data.Product;
import com.shop.product.service.data.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/products")
@org.springframework.stereotype.Controller
public class ProductController {


    private final ProductService productService;

    @GetMapping
    public String getAllProducts(@RequestParam(value = "query", required = false) String searchQuery,
                                 @RequestHeader(value = "X-User-Id", required = false) Long userId,
                                 Model model) {

        boolean isAuthenticated = userId != null && userId != 0;
        model.addAttribute("isAuthenticated", isAuthenticated);

        List<Product> products;
        if (searchQuery != null) {
            products = productService.searchProducts(searchQuery);
            model.addAttribute("searchQuery", searchQuery);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        return "mainpage";
    }


    @GetMapping("/product/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model,
                                   @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            boolean isAuthenticated = userId != null && userId != 0;
            model.addAttribute("isAuthenticated", isAuthenticated);
            return "product";
        } else {

            return "product-not-found";//заглушка
        }
    }
}
