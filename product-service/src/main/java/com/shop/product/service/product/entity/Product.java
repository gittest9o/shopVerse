package com.shop.product.service.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(length = 2048)
    private String imageUrl;
    private double rating;
    private int ratingCount;
}
