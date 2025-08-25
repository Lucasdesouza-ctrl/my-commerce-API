package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    CartItemDTO toDTO (CartItemEntity cartItem);

    CartItemEntity toEntity (CartItemDTO cartItemDTO);

}
