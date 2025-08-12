package me.study.my.ecommerce.dto;

public record CartItemDTO(String productName,
                          double unitPrice,
                          int quantity,
                          double totalItemPrice) {
}
