package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Controller
@RequestMapping("/editals")
@AllArgsConstructor
public class EditalController {

    private final EditalService editalService;
    private final EditalMapper editalMapper;
    private final EditalRepository editalRepository;

    @Tag(name = "Create Edital")
    @Operation(summary = "Create a new Edital")
    @PostMapping()
    public ResponseEntity<Void> createEdital(
            @Validated(CreateValidation.class)
            @RequestBody EditalDTO editalDto) {

        Edital edital = editalMapper.toEntity(editalDto);
        Edital savedEntity = editalService.createEdital(edital);
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

    @Tag(name="Search Editals with filter")
    @GetMapping
    @Operation(summary = "Search editals with filters or all editals")
    public ResponseEntity<ApplicationResponse<Page<EditalDTO>>> searchEditals(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            Pageable pageable) {

        Specification<Edital> specification = Specification.where(null);

        if (title != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (enabled != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enabled"), enabled));
        }
        Page<Edital> editalPage = editalRepository.findAll(specification, pageable);
        Page<EditalDTO> editalDTOPage = editalMapper.toDto(editalPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(editalDTOPage));
    }



}
