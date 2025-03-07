package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.core.StatusSolicitation;

import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/projects")

@AllArgsConstructor
public class ProjectsController {
    private final ProjectService projectService;

    @Tag(name = "Create Project")
    @Operation(summary = "Create a new project")
    @PostMapping( consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createProject(
            @RequestPart("dto") ProjectsDTO projectDto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request) throws IOException {

        Projects project = projectMapper.toEntity(projectDto);
        Projects savedEntity = projectService.createProject(project, file, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEntity.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    private final ProjectMapper projectMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Search race by ID")
    public ResponseEntity<ApplicationResponse<ProjectsDTO>> findRaceById(
            @PathVariable Long id) {
        Projects projects = projectService.findById(id);
        ProjectsDTO projectsDto = projectMapper.toDto(projects);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(projectsDto));
    }

    @Tag(name="Search Projects with filter")
    @GetMapping
    @Operation(summary = "Search Projects with filters or all Projects")
    public ResponseEntity<ApplicationResponse<Page<ProjectsDTO>>> searchProjects(Pageable pageable) {

        Specification<Projects> specification = Specification.where(null);

        Page<Projects> projectsPage = projectService.searchProjects(specification, pageable);
        Page<ProjectsDTO> projectsDTOPage = projectMapper.toDto(projectsPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(projectsDTOPage));
    }

    @Tag(name = "Update Project")
    @Operation(summary = "Update a project by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse<ProjectsDTO>> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectsDTO projectDto) {

        Projects project = projectMapper.toEntity(projectDto);
        Projects updatedProject = projectService.updateProject(id, p -> {
            p.setTitle(project.getTitle());
            p.setDescription(project.getDescription());
            p.setCoauthors(project.getCoauthors());
            p.setStatus(project.getStatus());
            // TODO: ADCIONAR O RESTANTE DOS CAMPOS
        });

        ProjectsDTO updatedProjectDto = projectMapper.toDto(updatedProject);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(updatedProjectDto));
    }

    @Tag(name = "Update Project Status")
    @Operation(summary = "Update the status of a project")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse<String>> updateProjectStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        StatusSolicitation statusEnum = StatusSolicitation.valueOf(status.toUpperCase());

        Projects updatedProject = projectService.updateStatus(id, statusEnum);

        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .body(ApplicationResponse.ofSuccess(updatedProject.getStatus().name()));
    }


    @Tag(name = "Delete Project")
    @Operation(summary = "Delete a project by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
