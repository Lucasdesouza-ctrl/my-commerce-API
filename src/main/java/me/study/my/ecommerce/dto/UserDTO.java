package me.study.my.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID id;
    private String name;
    private String email;
    private Long cpf;
    private LocalDate birthday;
}
