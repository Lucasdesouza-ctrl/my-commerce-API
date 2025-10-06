package me.study.my.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.LoginRequest;
import me.study.my.ecommerce.dto.LoginResponse;
import me.study.my.ecommerce.entity.RoleEntity;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
        var user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new BadCredentialsException("Email or password invalid !");
        }
        var now = Instant.now();
        var expireIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.get().getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expireIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expireIn));

    }
}
