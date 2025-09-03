package me.study.my.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.dto.UserMapper;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.RoleRepository;
import me.study.my.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper mapper;
    private final UserService service ;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userToCreate){
        UserDTO response = service.createUser(userToCreate);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<UserDTO>> findAll(){
        var response = service.findAll();

        return ResponseEntity.ok(response);
    }
}
