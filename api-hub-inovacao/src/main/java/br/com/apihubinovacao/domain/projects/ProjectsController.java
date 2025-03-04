package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.domain.enums.ProjectType;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;

import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/projects")

@AllArgsConstructor
public class ProjectsController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Tag(name="Create Project")
    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<Void> createProject(
            @Validated(CreateValidation.class)
            @RequestBody ProjectsDTO projectDto) {
        Projects project = projectMapper.toEntity(projectDto);
        Projects savedEntity = projectService.createProject(project);
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
    public ResponseEntity<ApplicationResponse<Page<ProjectsDTO>>> searchProjects(
            @RequestParam(value = "projectType", required = false) ProjectType projectType,
            @RequestParam(value = "status", required = false) StatusSolicitation status,
            @RequestParam(value = "String", required = false) String title,
            Pageable pageable) {

        Specification<Projects> specification = Specification.where(null);

        if (projectType != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("projectType"), projectType));
        }
        if (status != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), status));
        }
        if (title != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }

        Page<Projects> projectsPage = projectService.searchProjects(specification, pageable);
        Page<ProjectsDTO> projectsDTOPage = projectMapper.toDto(projectsPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(projectsDTOPage));
    }

}
