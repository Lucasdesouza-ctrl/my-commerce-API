package me.study.my.ecommerce.dto;

import java.util.UUID;

public record ProductDTO(String name, String description, double price,
                         int quantity) {
}
