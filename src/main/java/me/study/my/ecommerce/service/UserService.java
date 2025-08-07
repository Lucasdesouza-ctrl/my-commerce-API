package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.dto.UserMapper;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.RoleRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository cartRepository;

    public UserDTO createUser(UserCreateDTO userDTO) {
        var basicRole = roleRepository.findByName(RoleEntity.RolesEnum.BASIC.name());

        UserEntity user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Set.of(basicRole));
        UserEntity saved = userRepository.save(user);
        ShoppingCartEntity newCart = new ShoppingCartEntity();
        newCart.setUser(saved);
        cartRepository.save(newCart);

        return userMapper.toDTO(saved);
    }

    public List<UserEntity> findAll() {

        return userRepository.findAll();
    }
}
