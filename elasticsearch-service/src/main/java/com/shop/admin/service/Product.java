package com.shop.admin.service;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.math.BigDecimal;

@Data
@Document(indexName = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private double rating;
    private int ratingCount;
}
