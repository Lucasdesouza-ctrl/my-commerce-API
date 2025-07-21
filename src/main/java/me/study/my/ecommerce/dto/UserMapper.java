package me.study.my.ecommerce.dto;

import me.study.my.ecommerce.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO toDTO(UserEntity user);
    UserEntity toEntity(UserCreateDTO userDTO);
}
