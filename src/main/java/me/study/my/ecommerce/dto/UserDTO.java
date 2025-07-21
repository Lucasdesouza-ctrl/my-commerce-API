package me.study.my.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private String name;
    private String email;
    private Long cpf;
    private LocalDate birthday;
}
