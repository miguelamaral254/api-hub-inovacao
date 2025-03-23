package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Enterprise")
@RestController
@RequestMapping("/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseMapper enterpriseMapper;

    @Tag(name = "Create Enterprise")
    @PostMapping
    @Operation(summary = "Create a new enterprise")
    public ResponseEntity<Void> createEnterprise(
            @Validated(CreateValidation.class)
            @RequestBody EnterpriseDTO enterpriseDto) {

        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);
        Enterprise savedEntity = enterpriseService.createEnterprise(enterprise);

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

    @Tag(name = "Search Enterprises with filter")
    @GetMapping
    @Operation(summary = "Search enterprises with filters or all enterprises")
    public ResponseEntity<ApplicationResponse<Page<EnterpriseDTO>>> searchEnterprises(
            @RequestParam(value = "setor", required = false) String setorAtuacao,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "enterprisenome", required = false) String nomeEmpresa,
            Pageable pageable) {

        Specification<Enterprise> specification = Specification.where(null);

        if (setorAtuacao != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("setorAtuacao")), "%" + setorAtuacao.toLowerCase() + "%"));
        }
        if (cnpj != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("cnpj"), cnpj));
        }
        if (nomeEmpresa != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeEmpresa")), "%" + nomeEmpresa.toLowerCase() + "%"));
        }

        Page<Enterprise> enterprisePage = enterpriseService.searchEnterprises(specification, pageable);
        Page<EnterpriseDTO> enterpriseDTOPage = enterpriseMapper.toDto(enterprisePage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(enterpriseDTOPage));
    }

    @Tag(name = "Find Enterprise by ID")
    @GetMapping("/{id}")
    @Operation(summary = "Search enterprise by ID")
    public ResponseEntity<ApplicationResponse<EnterpriseDTO>> findById(
            @PathVariable Long id
    ) {
        Enterprise enterprise = enterpriseService.findById(id);
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(enterpriseDTO));
    }
/*
    @Tag(name = "Update Enterprise")
    @Operation(summary = "Update an existing enterprise")
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse<EnterpriseDTO>> updateEnterprise(
            @PathVariable Long id,
            @RequestBody EnterpriseDTO enterpriseDto) {

        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);
        Enterprise updatedEnterprise = enterpriseService.updateEnterprise(id, enterprise);
        EnterpriseDTO updatedEnterpriseDto = enterpriseMapper.toDto(updatedEnterprise);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(updatedEnterpriseDto));
    }

    @Tag(name = "Delete Enterprise")
    @Operation(summary = "Delete an enterprise by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id) {
        enterpriseService.deleteEnterprise(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

 */
}