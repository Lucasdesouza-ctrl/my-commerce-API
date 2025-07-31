package me.study.my.ecommerce.dto;

import java.util.UUID;

public record ProductAdmDTO(
        UUID id,
        String name,
        String description, double price,
                            int quantity) {
}
