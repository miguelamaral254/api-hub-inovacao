package br.com.apihubinovacao.domain.startup;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserService;
import br.com.apihubinovacao.validations.CreateValidation;
import br.com.apihubinovacao.validations.UpdateValidation;
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
@AllArgsConstructor
@RequestMapping("/startup")
public class StartupController {
    private final StartupService startupService;
    private final StartupMapper startupMapper;
    private final UserService userService;

    @Tag(name = "Create Startup")
    @PostMapping
    @Operation(summary = "Create a new Startup")
    public ResponseEntity<Void> createStartup(
            @Validated(CreateValidation.class)
            @RequestBody StartupDTO userDto) {

        Startup startup = startupMapper.toEntity(userDto);
        Startup savedEntity = startupService.createStartup(startup);

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
    @Operation(summary = "Find startup by ID")
    public ResponseEntity<ApplicationResponse<StartupDTO>> findStartupById(@PathVariable Long id) {
        Startup startup = startupService.findById(id);
        StartupDTO startupDto = startupMapper.toDto(startup);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(startupDto));
    }

    @Tag(name = "Search Startups with filter")
    @GetMapping
    @Operation(summary = "Search startups with filters or all startups")
    public ResponseEntity<ApplicationResponse<Page<StartupDTO>>> searchStartups(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "idUser", required = false) Long idUser,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            Pageable pageable) {

        Specification<Startup> specification = Specification.where(null);

        if (title != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (cnpj != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("cnpj"), cnpj));
        }

        if (idUser != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user").get("id"), idUser));
        }

        if (enabled != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enabled"), enabled));
        }

        Page<Startup> startupPage = startupService.searchStartup(specification, pageable);

        Page<StartupDTO> startupDTOPage = startupPage.map(startup -> startupMapper.toDto(startup));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(startupDTOPage));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Startup")
    public ResponseEntity<ApplicationResponse<StartupDTO>> updateStartup(
            @PathVariable Long id,
            @RequestBody StartupDTO startupDtoUpdates) {

        Startup updatedStartup = startupService.updateStartup(id, startup -> {
            if (startupDtoUpdates.title() != null) {
                startup.setTitle(startupDtoUpdates.title());
            }
            if (startupDtoUpdates.description() != null) {
                startup.setDescription(startupDtoUpdates.description());
            }
            if (startupDtoUpdates.enabled() != null) {
                startup.setEnabled(startupDtoUpdates.enabled());
            }
            if (startupDtoUpdates.status() != null) {
                startup.setStatus(startupDtoUpdates.status());
            }
            if (startupDtoUpdates.userId() != null) {
                User user = new User();
                user.setId(startupDtoUpdates.userId());
                startup.setUser(user);
            }
            if (startupDtoUpdates.managerId() != null) {
                User manager = new User();
                manager.setId(startupDtoUpdates.managerId());
                startup.setUserMenager(manager);
            }
        });

        StartupDTO updatedStartupDTO = startupMapper.toDto(updatedStartup);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(updatedStartupDTO));
    }
    @Tag(name = "Delete Startup")
    @Operation(summary = "Delete a Startup by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStartup(@PathVariable Long id) {
        startupService.deleteStartup(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}

