package com.shop.cart.service.services;

import com.shop.cart.service.data.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("api/products/{id}")
    CartItem getProductById(@PathVariable("id") Long id);
}
