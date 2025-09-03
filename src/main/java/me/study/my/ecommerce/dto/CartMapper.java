package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = ItemMapper.class)
public interface CartMapper {

    CartResponseDTO toDTO (ShoppingCartEntity cart);

    ShoppingCartEntity toEntity (CartResponseDTO cartDTO);
}
