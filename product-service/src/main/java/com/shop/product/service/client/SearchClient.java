package com.shop.product.service.client;

import com.shop.product.service.product.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "search-service")
public interface SearchClient {
    @GetMapping("/search")
    List<Product> search(@RequestParam("query") String query);
}
