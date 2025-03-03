package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserDTO;
import br.com.apihubinovacao.domain.users.UserMapper;
import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            @Validated(CreateValidation.class) @RequestBody ProjectsDTO projectDto) {
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

}
