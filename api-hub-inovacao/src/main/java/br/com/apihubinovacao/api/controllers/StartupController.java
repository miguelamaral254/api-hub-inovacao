package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.startups.StartupCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForStudent;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupsForStudentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/startup")
public class StartupController {
    private final ListStartupsForStudentUseCase listStartupsForStudentUseCase;
    private final CreateStartupForStudent createStartupForStudent;

    public StartupController(ListStartupsForStudentUseCase listStartupsForStudentUseCase, CreateStartupForStudent createStartupForStudent) {
        this.listStartupsForStudentUseCase = listStartupsForStudentUseCase;
        this.createStartupForStudent = createStartupForStudent;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/student/create")
    public ResponseEntity<StartupResponseStudentDTO> createStartup(@RequestBody StartupCreateStudentDTO dto) {
        StartupResponseStudentDTO createdStartupForStudent = createStartupForStudent.execute(dto);
        return ResponseEntity.ok(createdStartupForStudent);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/student/startups")
    public ResponseEntity<List<StartupResponseStudentDTO>> getAllStartups() {
        return ResponseEntity.ok(listStartupsForStudentUseCase.execute());
    }


}
