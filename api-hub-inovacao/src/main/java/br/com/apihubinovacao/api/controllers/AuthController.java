package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.LoginRequestDTO;
import br.com.apihubinovacao.domain.dtos.LoginResponseDTO;
import br.com.apihubinovacao.domain.services.JwtService;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.validateUserCredentials(loginRequest.email(), loginRequest.password());
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(
                new LoginResponseDTO(token, user.getEmail(), user.getRole().name(), "Login successful")
        );
    }
}
