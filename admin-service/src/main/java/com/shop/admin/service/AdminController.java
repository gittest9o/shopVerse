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

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductClient productClient;
    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;

    @GetMapping("/add/product")
    public String addProduct() {
        return "addProduct";
    }


    @PostMapping("/add/product")
    @Transactional
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product) {
        productClient.addProduct(product);
        kafkaTemplate.send("product-topic", product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reindex")
    @Transactional
    public ResponseEntity<?> reindex() {
        List <ProductDTO> products = productClient.getAllProducts;
        for(Product product : products){
             kafkaTemplate.send("product-topic", product);
        }

        return ResponseEntity.ok().build();
    }
}
