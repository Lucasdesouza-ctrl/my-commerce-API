package me.study.my.ecommerce.service;

import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private RoleEntity roleEntity;
    private UserCreateDTO userCreateDTO;
    private UserEntity userEntity;
    private UserDTO userDTO;


    void setup() {
        roleEntity = new RoleEntity();
        roleEntity.setName("BASIC");

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("");


        userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail("test@email.com");
        userEntity.setPassword("encodedPassword");
        userEntity.setRoles(Set.of(roleEntity));

        userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID());
        userDTO.setEmail("test@email.com");


    }

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            //arrange


            doReturn(userEntity).when(userRepository).save(any());
            var input = userCreateDTO;

            //act
            var output = userService.createUser(input);
            //assert

            assertNotNull(output);
        }
    }
}