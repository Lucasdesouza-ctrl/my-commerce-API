package me.study.my.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.LoginRequest;
import me.study.my.ecommerce.dto.LoginResponse;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
        var user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty() || user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Email or password invalid !");
        }
        var now = Instant.now();
        var expireIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.get().getName())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expireIn))
                .build();

        var jwtValue = "";

        return ResponseEntity.ok(new LoginResponse(jwtValue, expireIn));

    }
}
