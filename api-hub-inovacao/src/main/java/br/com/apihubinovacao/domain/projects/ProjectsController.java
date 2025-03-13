package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.core.StatusSolicitation;
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
    private final ProjectMapper projectMapper;

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



    @GetMapping("/{id}")
    @Operation(summary = "Search race by ID")
    public ResponseEntity<ApplicationResponse<ProjectsDTO>> findRaceById(
            @PathVariable Long id
    ) {
        Projects projects = projectService.findById(id);
        ProjectsDTO projectsDto = projectMapper.toDto(projects);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(projectsDto));
    }

    @Tag(name="Search Projects with filter")
    @GetMapping
    @Operation(summary = "Search projects with filters or all projects")
    public ResponseEntity<ApplicationResponse<Page<ProjectsDTO>>> searchProjects(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "projecttype", required = false) String projectType,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "idmanager", required = false) Long idmanager,
            @RequestParam(value = "iduser", required = false) Long iduser,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            Pageable pageable) {

        Specification<Projects> specification = Specification.where(null);

        if (title != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (projectType != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("projectType")), projectType.toLowerCase()));
        }

        if (status != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")), status.toLowerCase()));
        }

        if (idmanager != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idManager").get("id"), idmanager));
        }
        if (iduser != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user").get("id"), iduser));
        }
        if (enabled != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enabled"), enabled));
        }

        Page<Projects> projectPage = projectService.searchProjects(specification, pageable);
        Page<ProjectsDTO> projectDTOPage = projectMapper.toDto(projectPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(projectDTOPage));
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
