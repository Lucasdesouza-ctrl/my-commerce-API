package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface ProductMapper {


    ProductDTO toDTO (ProductEntity product);


    ProductEntity toEntity(ProductDTO productDTO);

    ProductAdmDTO productAdmtoDTO (ProductEntity product);
}
