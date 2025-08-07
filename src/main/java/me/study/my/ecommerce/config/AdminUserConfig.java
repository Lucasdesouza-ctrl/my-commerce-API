package me.study.my.ecommerce.config;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.RoleRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;


@RequiredArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.cpf}")
    private String adminCPF;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(RoleEntity.RolesEnum.ADMIN.name());
        var userAdmin = userRepository.findByName("admin");

        userAdmin.ifPresentOrElse(
                user -> System.out.println("Admin already exists!!"),
                ()-> {
                    var user = new UserEntity();
                    user.setName("Admin");
                    user.setPassword(encoder.encode(adminPassword));
                    user.setEmail(adminEmail);
                    user.setCpf(adminCPF);
                    user.setBirthday(LocalDate.now());
                    user.setRoles(Set.of(roleAdmin));

                    userRepository.save(user);
                }
        );

    }
}
