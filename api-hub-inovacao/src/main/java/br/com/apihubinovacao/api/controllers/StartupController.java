package br.com.apihubinovacao.api.controllers;


import br.com.apihubinovacao.domain.dtos.startups.*;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForProfessor;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForStudent;
import br.com.apihubinovacao.domain.usecases.startup.get.ListAllStartupsUseCase;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupByUserEmailUseCase;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupsForProfessorUseCase;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupsForStudentUseCase;
import br.com.apihubinovacao.domain.usecases.startup.update.UpdateStartupDetailsUseCase;
import br.com.apihubinovacao.domain.usecases.startup.update.UpdateStartupStatusUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/startup")
public class StartupController {
    private final ListStartupsForStudentUseCase listStartupsForStudentUseCase;
    private final CreateStartupForStudent createStartupForStudent;
    private final CreateStartupForProfessor createStartupForProfessor;
    private final ListStartupsForProfessorUseCase listStartupsForProfessorUseCase;
    private final ListAllStartupsUseCase listAllStartupsUseCase;
    private final ListStartupByUserEmailUseCase listStartupByUserEmailUseCase;
    private final UpdateStartupStatusUseCase updateStartupStatusUseCase;



    public StartupController(
            ListStartupsForStudentUseCase listStartupsForStudentUseCase,
            CreateStartupForStudent createStartupForStudent,
            CreateStartupForProfessor createStartupForProfessor,
            ListStartupsForProfessorUseCase listStartupsForProfessorUseCase,
            ListAllStartupsUseCase listAllStartupsUseCase,
            ListStartupByUserEmailUseCase listStartupByUserEmailUseCase,
            UpdateStartupStatusUseCase updateStartupStatusUseCase)
    {
        this.listStartupsForStudentUseCase = listStartupsForStudentUseCase;
        this.createStartupForStudent = createStartupForStudent;
        this.createStartupForProfessor = createStartupForProfessor;
        this.listStartupsForProfessorUseCase = listStartupsForProfessorUseCase;
        this.listAllStartupsUseCase = listAllStartupsUseCase;
        this.listStartupByUserEmailUseCase = listStartupByUserEmailUseCase;
        this.updateStartupStatusUseCase = updateStartupStatusUseCase;

    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/student/create")
    public ResponseEntity<StartupResponseStudentDTO> createStartup(@RequestBody StartupCreateStudentDTO dto) {
        StartupResponseStudentDTO createdStartupForStudent = createStartupForStudent.execute(dto);
        return ResponseEntity.ok(createdStartupForStudent);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping("/professor/create")
    public ResponseEntity<StartupResponseProfessorDTO> createStartup(@RequestBody StartupCreateProfessorDTO dto) {
        StartupResponseProfessorDTO createdStartupForProfessor = createStartupForProfessor.execute(dto);
        return ResponseEntity.ok(createdStartupForProfessor);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/student/startups")
    public ResponseEntity<List<StartupResponseStudentDTO>> getAllStartupsStudent() {
        return ResponseEntity.ok(listStartupsForStudentUseCase.execute());
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @GetMapping("/professor/startups")
    public ResponseEntity<List<StartupResponseProfessorDTO>> getAllStartupsProfessor() {
        return ResponseEntity.ok(listStartupsForProfessorUseCase.execute());
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllStartups() {
        List<?> startups = listAllStartupsUseCase.execute();
        return ResponseEntity.ok(startups);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/by-email")
    public ResponseEntity<List<?>> getProjectsByUserEmail(@RequestParam String email) {
        List<?> startup = listStartupByUserEmailUseCase.execute(email);
        return ResponseEntity.ok(startup);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PutMapping("{startupId}/status")
    public ResponseEntity<Void> updateStatusStartup(
            @PathVariable Long startupId,
            @RequestBody UpdateStartupStatusDTO dto
    ){
        updateStartupStatusUseCase.execute(startupId, dto);
        return ResponseEntity.noContent().build();

    }


}
