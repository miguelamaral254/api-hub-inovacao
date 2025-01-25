package br.com.apihubinovacao.api.controllers;


import br.com.apihubinovacao.domain.dtos.UserCreateDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/post")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

        /*

         @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        // Lógica para buscar o usuário
        return ResponseEntity.ok(/* Retorne o DTO aqui */;



}
