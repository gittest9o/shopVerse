package com.shop.elasticsearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class ProductConsumer {

    private final ProductRepository repository;

    @KafkaListener(topics = "product-topic", groupId = "elasticsearch-group")
    public void consume(Product product) {
        System.out.println("Indexing product: " + product.getName());
        repository.save(product);
    }
}
