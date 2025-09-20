package com.shop.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductClient productClient;
    private final KafkaTemplate<String, Product> kafkaTemplate;

    @GetMapping("/add/product")
    public String addProduct() {
        return "addProduct";
    }


    @PostMapping("/add/product")
    @Transactional
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        productClient.addProduct(product);
        kafkaTemplate.send("product-topic", product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reindex")
    public ResponseEntity<?> reindex() {
        List<Product> products = productClient.getAllProducts();
        for(Product product : products){
             kafkaTemplate.send("product-topic", product);
        }

        return ResponseEntity.ok().build();
    }
}
