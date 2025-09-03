package me.study.my.ecommerce.dto;

import java.util.List;


public record CartResponseDTO(List<CartItemDTO> itens,
                              double totalPrice) {
}
