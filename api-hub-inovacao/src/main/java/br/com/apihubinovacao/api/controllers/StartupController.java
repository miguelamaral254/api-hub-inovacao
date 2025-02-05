package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.startups.StartupCreateProfessorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseProfessorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForProfessor;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForStudent;
import br.com.apihubinovacao.domain.usecases.startup.get.ListAllStartupsUseCase;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupByUserEmailUseCase;
import br.com.apihubinovacao.domain.usecases.startup.get.ListStartupsForProfessorUseCase;
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
    private final CreateStartupForProfessor createStartupForProfessor;
    private final ListStartupsForProfessorUseCase listStartupsForProfessorUseCase;
    private final ListAllStartupsUseCase listAllStartupsUseCase;
    private final ListStartupByUserEmailUseCase listStartupByUserEmailUseCase;

    public StartupController(ListStartupsForStudentUseCase listStartupsForStudentUseCase, CreateStartupForStudent createStartupForStudent, CreateStartupForProfessor createStartupForProfessor, ListStartupsForProfessorUseCase listStartupsForProfessorUseCase, ListAllStartupsUseCase listAllStartupsUseCase, ListStartupByUserEmailUseCase listStartupByUserEmailUseCase) {
        this.listStartupsForStudentUseCase = listStartupsForStudentUseCase;
        this.createStartupForStudent = createStartupForStudent;
        this.createStartupForProfessor = createStartupForProfessor;
        this.listStartupsForProfessorUseCase = listStartupsForProfessorUseCase;
        this.listAllStartupsUseCase = listAllStartupsUseCase;
        this.listStartupByUserEmailUseCase = listStartupByUserEmailUseCase;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/student/create")
    public ResponseEntity<StartupResponseStudentDTO> createStartup(@RequestBody StartupCreateStudentDTO dto) {
        StartupResponseStudentDTO createdStartupForStudent = createStartupForStudent.execute(dto);
        return ResponseEntity.ok(createdStartupForStudent);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/student/startups")
    public ResponseEntity<List<StartupResponseStudentDTO>> getAllStartupsStudent() {
        return ResponseEntity.ok(listStartupsForStudentUseCase.execute());
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping("/professor/create")
    public ResponseEntity<StartupResponseProfessorDTO> createStartup(@RequestBody StartupCreateProfessorDTO dto) {
        StartupResponseProfessorDTO createdStartupForProfessor = createStartupForProfessor.execute(dto);
        return ResponseEntity.ok(createdStartupForProfessor);
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


}
