package me.study.my.ecommerce.dto;

import java.util.UUID;

public record CartItemDTO(
        UUID id,
        String productName,
        double unitPrice,
        int quantity
) {
}
