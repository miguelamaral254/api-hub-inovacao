package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.UserCreateDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/by-email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all-platform-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllPlatformUsers() {
        List<UserResponseDTO> platformUsers = userService.getAllPlatformUsers();
        return ResponseEntity.ok(platformUsers);
    }
}
