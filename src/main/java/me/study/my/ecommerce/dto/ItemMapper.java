package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "productName", expression = "java(cartItem.getProduct().getName())")
    CartItemDTO toDTO (CartItemEntity cartItem);


    CartItemEntity toEntity (CartItemDTO cartItemDTO);

}
