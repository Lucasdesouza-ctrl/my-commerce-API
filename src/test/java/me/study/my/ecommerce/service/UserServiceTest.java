package me.study.my.ecommerce.service;

import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.dto.UserMapper;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.RoleRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private  UserMapper userMapper;

    @Mock
    private  BCryptPasswordEncoder passwordEncoder;

    @Mock
    private  RoleRepository roleRepository;

    @Mock
    private  ShoppingCartRepository cartRepository;

    @InjectMocks
    private UserService userService;

    private UserCreateDTO userCreateDTO;
    private RoleEntity roleEntity;
    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("user");
        userCreateDTO.setEmail("user@email.com");
        userCreateDTO.setPassword("password");
        userCreateDTO.setCpf("12345678999");
        userCreateDTO.setBirthday(LocalDate.of(2000,12,20));

        roleEntity = new RoleEntity();
        roleEntity.setName("BASIC");
        roleEntity.setRoleId(1L);

        userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName("user");
        userEntity.setEmail("user@email.com");
        userEntity.setPassword("encodedPassword");
        userEntity.setRoles(Set.of(roleEntity));

        userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());

    }


    @Test
    @DisplayName("Should create user with success")
    public void shouldCreateUserWithSuccess(){

        //arrange

        when(roleRepository.findByName("BASIC")).thenReturn(roleEntity);
        when(userMapper.toEntity(userCreateDTO)).thenReturn(userEntity);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        //act

        UserDTO result = userService.createUser(userCreateDTO);

        //assert
        assertNotNull(result);
        assertEquals("user@email.com", result.getEmail());

        verify(roleRepository).findByName("BASIC");
        verify(passwordEncoder).encode("password");

    }


}