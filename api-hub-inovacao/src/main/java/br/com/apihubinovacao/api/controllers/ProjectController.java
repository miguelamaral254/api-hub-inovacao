package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectCreateProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentDTO;
import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForProfessorUseCase;
import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForStudentUseCase;
import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsByUserEmailUseCase;
import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsForProfessorUseCase;
import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsForStudentUseCase;
import br.com.apihubinovacao.domain.usecases.projects.get.ListAllAcademicProjectsUseCase; // Novo UseCase
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase;
    private final CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase;
    private final ListAcademicProjectsForProfessorUseCase listAcademicProjectsForProfessorUseCase;
    private final ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase;
    private final ListAcademicProjectsByUserEmailUseCase listAcademicProjectsByUserEmailUseCase;
    private final ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase; // Novo UseCase

    @Autowired
    public ProjectController(
            CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase,
            CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase,
            ListAcademicProjectsForProfessorUseCase listAcademicProjectsForProfessorUseCase,
            ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase,
            ListAcademicProjectsByUserEmailUseCase listAcademicProjectsByUserEmailUseCase,
            ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase) { // Novo UseCase
        this.createAcademicProjectForProfessorUseCase = createAcademicProjectForProfessorUseCase;
        this.createAcademicProjectForStudentUseCase = createAcademicProjectForStudentUseCase;
        this.listAcademicProjectsForProfessorUseCase = listAcademicProjectsForProfessorUseCase;
        this.listAcademicProjectsForStudentUseCase = listAcademicProjectsForStudentUseCase;
        this.listAcademicProjectsByUserEmailUseCase = listAcademicProjectsByUserEmailUseCase;
        this.listAllAcademicProjectsUseCase = listAllAcademicProjectsUseCase; // Injeção do novo UseCase
    }

    // Endpoint para criar um projeto para um professor
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGERS')")
    @PostMapping("/professor/create")
    public ResponseEntity<AcademicProjectResponseProfessorDTO> createProjectForProfessor(
            @RequestBody AcademicProjectCreateProfessorDTO dto) {
        AcademicProjectResponseProfessorDTO createdProject = createAcademicProjectForProfessorUseCase.execute(dto);
        return ResponseEntity.ok(createdProject);
    }

    // Endpoint para criar um projeto para um estudante
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGERS')")
    @PostMapping("/student/create")
    public ResponseEntity<AcademicProjectResponseStudentDTO> createProjectForStudent(
            @RequestBody AcademicProjectCreateStudentDTO dto) {
        AcademicProjectResponseStudentDTO createdProject = createAcademicProjectForStudentUseCase.execute(dto);
        return ResponseEntity.ok(createdProject);
    }

    // Endpoint para listar todos os projetos de professores
    @PreAuthorize("hasAnyRole('PROFESSOR','ADMIN', 'MANAGERS')")
    @GetMapping("/all-professor")
    public ResponseEntity<List<AcademicProjectResponseProfessorDTO>> getAllProjectsForProfessor() {
        List<AcademicProjectResponseProfessorDTO> projects = listAcademicProjectsForProfessorUseCase.execute();
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN', 'MANAGERS')")
    @GetMapping("/all-student")
    public ResponseEntity<List<AcademicProjectResponseStudentDTO>> getAllProjectsForStudent() {
        List<AcademicProjectResponseStudentDTO> projects = listAcademicProjectsForStudentUseCase.execute();
        return ResponseEntity.ok(projects);
    }

    // Endpoint para listar projetos pelo e-mail do usuário (professor ou estudante)
    @PreAuthorize("hasAnyRole('PROFESSOR','STUDENT', 'ADMIN', 'MANAGERS')")
    @GetMapping("/by-email")
    public ResponseEntity<List<?>> getProjectsByUserEmail(@RequestParam String email) {
        List<?> projects = listAcademicProjectsByUserEmailUseCase.execute(email);
        return ResponseEntity.ok(projects);
    }

    // Novo Endpoint: Listar todos os projetos, independentemente do papel do autor
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllProjects() {
        List<?> projects = listAllAcademicProjectsUseCase.execute();
        return ResponseEntity.ok(projects);
    }
}