package com.shop.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/api/products/add")
     void addProduct(@RequestBody Product product);

    @GetMapping("/api/products/get")
     List <Product> getAllProducts();
}
