package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.user.UserCreateCnpjDTO;
import br.com.apihubinovacao.domain.dtos.user.UserCreateCpfDTO;
import br.com.apihubinovacao.domain.dtos.user.UserResponseCnpjDTO;
import br.com.apihubinovacao.domain.dtos.user.UserResponseCpfDTO;
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
    public ResponseEntity<UserResponseCnpjDTO> createUserWithCnpj(@RequestBody UserCreateCnpjDTO dto) {
        UserResponseCnpjDTO createdUser = createUserWithCnpjUseCase.execute(dto);
        return ResponseEntity.ok(createdUser);
    }


    @PostMapping("/create-user-cpf")
    public ResponseEntity<UserResponseCpfDTO> createUserWithCpf(@RequestBody UserCreateCpfDTO dto) {
        UserResponseCpfDTO createdUser = createUserWithCpfUseCase.execute(dto);
        return ResponseEntity.ok(createdUser);
    }


    @GetMapping("/by-email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Object> getUserByEmail(@RequestParam String email) {
        Object user = getUserByEmailUseCase.execute(email);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/all-users")
    public ResponseEntity<List<Object>> getAllUsers(@RequestParam Role role) {
        List<Object> users = getAllUsersUseCase.execute(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all-platform-users")
    public ResponseEntity<Page<Object>> getAllPlatformUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Object> platformUsers = getAllPlatformUsersUseCase.execute(page, size);
        return ResponseEntity.ok(platformUsers);
    }
}