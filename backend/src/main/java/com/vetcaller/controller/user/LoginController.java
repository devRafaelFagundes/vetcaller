package com.vetcaller.controller.user;

import com.vetcaller.controller.user.dto.request.LoginRequest;
import com.vetcaller.controller.user.dto.response.LoginResponse;
import com.vetcaller.domain.Permission;
import com.vetcaller.domain.User;
import com.vetcaller.service.user.FindUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final FindUserService findUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginController(FindUserService findUserService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.findUserService = findUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = findUserService.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty() || !isLoginCorrect(loginRequest.getPassword(), optionalUser.get().getPassword())) {
            throw new BadCredentialsException("Usuário ou senha incorretos!");
        }

        User user = optionalUser.get();
        List<String> permissions = user.getPermissions().stream()
                .map(Permission::getName)
                .toList();
        long expiresIn = 600L;

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("seguranca-api")
                .subject(user.getName())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("email", user.getEmail())
                .claim("scope", permissions)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }

    private boolean isLoginCorrect(String password, String savedPassword) {
        return passwordEncoder.matches(password, savedPassword);
    }
}
