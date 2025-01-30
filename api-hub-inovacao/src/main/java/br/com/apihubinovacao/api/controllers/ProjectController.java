package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.AcademicProjectCreateDTO;
import br.com.apihubinovacao.domain.dtos.AcademicProjectResponseDTO;

import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectUseCase;
import br.com.apihubinovacao.domain.usecases.projects.create.ListAcademicProjectsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateAcademicProjectUseCase createAcademicProjectUseCase;
    private final ListAcademicProjectsUseCase listAcademicProjectsUseCase;

    @Autowired
    public ProjectController(
            CreateAcademicProjectUseCase createAcademicProjectUseCase,
            ListAcademicProjectsUseCase listAcademicProjectsUseCase) {
        this.createAcademicProjectUseCase = createAcademicProjectUseCase;
        this.listAcademicProjectsUseCase = listAcademicProjectsUseCase;
    }

    /**
     * Criar um novo projeto acadêmico
     */
    @PostMapping("/create")
    public ResponseEntity<AcademicProjectResponseDTO> createProject(@RequestBody AcademicProjectCreateDTO dto) {
        AcademicProjectResponseDTO createdProject = createAcademicProjectUseCase.execute(dto);
        return ResponseEntity.ok(createdProject);
    }

    /**
     * Listar todos os projetos acadêmicos
     */
    @GetMapping("/all")
    public ResponseEntity<List<AcademicProjectResponseDTO>> getAllProjects() {
        List<AcademicProjectResponseDTO> projects = listAcademicProjectsUseCase.execute();
        return ResponseEntity.ok(projects);
    }
}