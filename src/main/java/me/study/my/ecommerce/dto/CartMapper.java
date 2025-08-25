package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;

public interface CartMapper {

    CartResponseDTO toDTO (ShoppingCartEntity cart);

    ShoppingCartEntity toEntity (CartResponseDTO cartDTO);
}
