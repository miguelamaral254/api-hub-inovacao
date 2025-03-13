package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.user.UserCreateCpfDTO;
import br.com.apihubinovacao.domain.dtos.user.UserResponseCpfDTO;
import br.com.apihubinovacao.domain.usecases.user.create.CreateManagerUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CreateManagerUserUseCase createManagerUserUseCase;

    @Autowired
    public AdminController(CreateManagerUserUseCase createManagerUserUseCase) {
        this.createManagerUserUseCase = createManagerUserUseCase;
    }


    @PostMapping("/create-manager")
    public ResponseEntity<UserResponseCpfDTO> createManager(@RequestBody UserCreateCpfDTO dto) {
        UserResponseCpfDTO createdManager = createManagerUserUseCase.execute(dto);
        return ResponseEntity.ok(createdManager);
    }
}