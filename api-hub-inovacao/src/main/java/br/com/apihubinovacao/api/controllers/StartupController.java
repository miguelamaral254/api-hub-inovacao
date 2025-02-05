package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.startups.StartupCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.usecases.startup.create.CreateStartupForStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/startup")
public class StartupController {
    private final CreateStartupForStudent createStartupForStudent;

    public StartupController(CreateStartupForStudent createStartupForStudent) {
        this.createStartupForStudent = createStartupForStudent;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/student/create")
    public ResponseEntity<StartupResponseStudentDTO> createStartup(@RequestBody StartupCreateStudentDTO dto) {
        StartupResponseStudentDTO createdStartupForStudent = createStartupForStudent.execute(dto);
        return ResponseEntity.ok(createdStartupForStudent);
    }
}
