package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.*;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.usecases.user.create.CreateUserWithCnpjUseCase;
import br.com.apihubinovacao.domain.usecases.user.create.CreateUserWithCpfUseCase;
import br.com.apihubinovacao.domain.usecases.user.get.GetAllPlatformUsersUseCase;
import br.com.apihubinovacao.domain.usecases.user.get.GetAllUsersUseCase;
import br.com.apihubinovacao.domain.usecases.user.get.GetUserByEmailUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserWithCnpjUseCase createUserWithCnpjUseCase;
    private final CreateUserWithCpfUseCase createUserWithCpfUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetAllPlatformUsersUseCase getAllPlatformUsersUseCase;

    @Autowired
    public UserController(
            CreateUserWithCnpjUseCase createUserWithCnpjUseCase,
            CreateUserWithCpfUseCase createUserWithCpfUseCase,
            GetUserByEmailUseCase getUserByEmailUseCase,
            GetAllUsersUseCase getAllUsersUseCase,
            GetAllPlatformUsersUseCase getAllPlatformUsersUseCase) {
        this.createUserWithCnpjUseCase = createUserWithCnpjUseCase;
        this.createUserWithCpfUseCase = createUserWithCpfUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getAllPlatformUsersUseCase = getAllPlatformUsersUseCase;
    }


    @PostMapping("/create-user-cnpj")
    public ResponseEntity<UserResponseDTO> createUserWithCnpj(@RequestBody UserCreateCnpjDTO dto) {
        UserResponseDTO createdUser = createUserWithCnpjUseCase.execute(dto);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Criar usuário com CPF (Manager, Student ou Professor)
     */
    @PostMapping("/create-user-cpf")
    public ResponseEntity<UserResponseDTO> createUserWithCpf(@RequestBody UserCreateCpfDTO dto) {
        UserResponseDTO createdUser = createUserWithCpfUseCase.execute(dto);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Buscar usuário por e-mail
     */
    @GetMapping("/by-email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        UserResponseDTO user = getUserByEmailUseCase.execute(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Buscar todos os usuários ativos filtrando por Role
     */
    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam Role role) {
        List<UserResponseDTO> users = getAllUsersUseCase.execute(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Buscar todos os usuários da plataforma de forma paginada
     */
    @GetMapping("/all-platform-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getAllPlatformUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponseDTO> platformUsers = getAllPlatformUsersUseCase.execute(page, size);
        return ResponseEntity.ok(platformUsers);
    }
}