package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.auth.LoginRequestDTO;
import br.com.apihubinovacao.domain.dtos.auth.LoginResponseDTO;
import br.com.apihubinovacao.domain.services.JwtService;
import br.com.apihubinovacao.domain.models.users.UserBase;
import br.com.apihubinovacao.domain.usecases.user.validate.ValidateUserCredentialsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final ValidateUserCredentialsUseCase validateUserCredentialsUseCase;

    @Autowired
    public AuthController(JwtService jwtService, ValidateUserCredentialsUseCase validateUserCredentialsUseCase) {
        this.jwtService = jwtService;
        this.validateUserCredentialsUseCase = validateUserCredentialsUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        UserBase user = validateUserCredentialsUseCase.execute(loginRequest.email(), loginRequest.password());
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(
                new LoginResponseDTO(token, user.getEmail(), user.getRole().name(), "Login successful")
        );
    }
}