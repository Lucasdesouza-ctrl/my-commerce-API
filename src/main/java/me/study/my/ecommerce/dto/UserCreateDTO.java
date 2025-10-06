package me.study.my.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class UserCreateDTO {
    private String name;
    
    @Email
    private String email;

    private String password;

    private String cpf;

    private LocalDate birthday;
}
