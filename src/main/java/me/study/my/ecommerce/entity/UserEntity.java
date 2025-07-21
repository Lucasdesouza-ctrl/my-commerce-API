package me.study.my.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.study.my.ecommerce.dto.LoginRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private String email;

    @JsonIgnore
    @Column(name = "hash_password")
    private String password;

    private RoleEntity role;

    private String cpf;

    @Column(name = "birthday")
    private LocalDate birthday;


    public Boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder){

        return passwordEncoder.matches(loginRequest.password(), this.password);

    }
}
