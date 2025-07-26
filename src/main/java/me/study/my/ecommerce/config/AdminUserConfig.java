package me.study.my.ecommerce.config;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.RoleRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;


@RequiredArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(RoleEntity.RolesEnum.ADMIN.name());

        var userAdmin = userRepository.findByName("admin");

        userAdmin.ifPresentOrElse(
                user -> System.out.println("Admin ja existe"),
                ()-> {
                    var user = new UserEntity();
                    user.setName("Admin");
                    user.setPassword(encoder.encode("admin3216"));
                    user.setCpf("12312312312");
                    user.setEmail("admin@gmail.com");
                    user.setBirthday(LocalDate.now());
                    user.setRoles(Set.of(roleAdmin));

                    userRepository.save(user);
                }
        );

    }
}
