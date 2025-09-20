package com.shop.admin.service;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private double rating;
        private int ratingCount;
}
