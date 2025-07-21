package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.dto.UserMapper;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final UserRepository userRepository;


    public UserDTO createUser (UserCreateDTO userDTO){
        UserEntity user = userMapper.toEntity(userDTO);
        user.setPassword(userDTO.getPassword());
        UserEntity saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }
}
