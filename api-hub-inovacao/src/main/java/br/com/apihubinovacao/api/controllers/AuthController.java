package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.LoginRequestDTO;
import br.com.apihubinovacao.domain.dtos.LoginResponseDTO;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.UserRepository;
import br.com.apihubinovacao.domain.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(new LoginResponseDTO(null, null, null, "Invalid credentials"));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(new LoginResponseDTO(null, null, null, "Invalid credentials"));
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getEmail(), user.getRole().name(), "Login successful"));
    }
}
