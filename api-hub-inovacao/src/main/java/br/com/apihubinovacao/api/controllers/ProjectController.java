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

    @Autowired
    public ProjectController(
            CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase,
            CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase,
            ListAcademicProjectsForProfessorUseCase listAcademicProjectsForProfessorUseCase,
            ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase,
            ListAcademicProjectsByUserEmailUseCase listAcademicProjectsByUserEmailUseCase) {
        this.createAcademicProjectForProfessorUseCase = createAcademicProjectForProfessorUseCase;
        this.createAcademicProjectForStudentUseCase = createAcademicProjectForStudentUseCase;
        this.listAcademicProjectsForProfessorUseCase = listAcademicProjectsForProfessorUseCase;
        this.listAcademicProjectsForStudentUseCase = listAcademicProjectsForStudentUseCase;
        this.listAcademicProjectsByUserEmailUseCase = listAcademicProjectsByUserEmailUseCase;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGERS')")
    @PostMapping("/professor/create")
    public ResponseEntity<AcademicProjectResponseProfessorDTO> createProjectForProfessor(@RequestBody AcademicProjectCreateProfessorDTO dto) {
        AcademicProjectResponseProfessorDTO createdProject = createAcademicProjectForProfessorUseCase.execute(dto);
        return ResponseEntity.ok(createdProject);
    }
    @PostMapping("/student/create")
    public ResponseEntity<AcademicProjectResponseStudentDTO> createProjectForStudent(@RequestBody AcademicProjectCreateStudentDTO dto) {
        AcademicProjectResponseStudentDTO createdProject = createAcademicProjectForStudentUseCase.execute(dto);
        return ResponseEntity.ok(createdProject);
    }


    @GetMapping("/all-professor")
    public ResponseEntity<List<AcademicProjectResponseProfessorDTO>> getAllProjectsForProfessor() {
        List<AcademicProjectResponseProfessorDTO> projects = listAcademicProjectsForProfessorUseCase.execute();
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGERS')")
    @GetMapping("/all-student")
    public ResponseEntity<List<AcademicProjectResponseStudentDTO>> getAllProjectsForStudent() {
        List<AcademicProjectResponseStudentDTO> projects = listAcademicProjectsForStudentUseCase.execute();
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGERS')")
    @GetMapping("/by-email")
    public ResponseEntity<List<?>> getProjectsByUserEmail(@RequestParam String email) {
        List<?> projects = listAcademicProjectsByUserEmailUseCase.execute(email);
        return ResponseEntity.ok(projects);
    }
}
