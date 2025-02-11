package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.projects.*;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;
import br.com.apihubinovacao.domain.services.ImageService;
import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForProfessorUseCase;
import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForStudentUseCase;
import br.com.apihubinovacao.domain.usecases.projects.get.*;
import br.com.apihubinovacao.domain.usecases.projects.update.UpdateAcademicProjectDetailsUseCase;
import br.com.apihubinovacao.domain.usecases.projects.update.UpdateAcademicProjectStatusUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase;
    private final CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase;
    private final ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase;
    private final ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase; // Novo UseCase
    private final UpdateAcademicProjectStatusUseCase updateAcademicProjectStatusUseCase;
    private final UpdateAcademicProjectDetailsUseCase updateAcademicProjectDetailsUseCase;
    private final ListAllAcademicProjectsForManagerUseCase listAllAcademicProjectsForManagerUseCase;
    private final ImageService imageService;

    @Autowired
    public ProjectController(
            CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase,
            CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase,
            ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase,
            ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase, UpdateAcademicProjectStatusUseCase updateAcademicProjectStatusUseCase, UpdateAcademicProjectDetailsUseCase updateAcademicProjectDetailsUseCase, ListAllAcademicProjectsForManagerUseCase listAllAcademicProjectsForManagerUseCase, ImageService imageService) { // Novo UseCase
        this.createAcademicProjectForProfessorUseCase = createAcademicProjectForProfessorUseCase;
        this.createAcademicProjectForStudentUseCase = createAcademicProjectForStudentUseCase;
        this.listAcademicProjectsForStudentUseCase = listAcademicProjectsForStudentUseCase;
        this.listAllAcademicProjectsUseCase = listAllAcademicProjectsUseCase;
        this.updateAcademicProjectStatusUseCase = updateAcademicProjectStatusUseCase;
        this.updateAcademicProjectDetailsUseCase = updateAcademicProjectDetailsUseCase;
        this.listAllAcademicProjectsForManagerUseCase = listAllAcademicProjectsForManagerUseCase;
        this.imageService = imageService;
    }

    @PostMapping(value = "/professor/create", consumes = {"multipart/form-data"})
    public ResponseEntity<AcademicProjectResponseProfessorDTO> createProjectForProfessor(
            @RequestPart("dto") AcademicProjectCreateProfessorDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            HttpServletRequest request) {

        AcademicProjectResponseProfessorDTO createdProject = createAcademicProjectForProfessorUseCase.execute(dto, imageFile, request);
        return ResponseEntity.ok(createdProject);
    }

    @PostMapping(value = "/student/create", consumes = {"multipart/form-data"})
    public ResponseEntity<AcademicProjectResponseStudentDTO> createProjectForStudent(
            @RequestPart("dto") AcademicProjectCreateStudentDTO dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            HttpServletRequest request) {

        AcademicProjectResponseStudentDTO createdProject = createAcademicProjectForStudentUseCase.execute(dto, imageFile, request);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllProjects() {
        List<?> projects = listAllAcademicProjectsUseCase.execute();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{projectId}/status")
    public ResponseEntity<Void> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestBody UpdateAcademicProjectStatusDTO dto) {
        updateAcademicProjectStatusUseCase.execute(projectId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/details")
    public ResponseEntity<Void> updateProjectDetails(
            @PathVariable Long projectId,
            @RequestBody UpdateAcademicProjectDetailsDTO updateDTO) {
        updateAcademicProjectDetailsUseCase.execute(projectId, updateDTO);
        return ResponseEntity.noContent().build();
    }
}