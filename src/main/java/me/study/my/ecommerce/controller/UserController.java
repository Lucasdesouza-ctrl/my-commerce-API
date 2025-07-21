package me.study.my.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.UserCreateDTO;
import me.study.my.ecommerce.dto.UserDTO;
import me.study.my.ecommerce.dto.UserMapper;
import me.study.my.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class UserController {

    private final UserMapper mapper;
    private final UserService service ;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userToCreate){
        UserDTO response = service.createUser(userToCreate);
        return ResponseEntity.ok(response);
    }
}
