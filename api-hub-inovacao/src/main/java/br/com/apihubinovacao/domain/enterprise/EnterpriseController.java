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

    @Tag(name = "Update Enterprise")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing enterprise")
    public ResponseEntity<ApplicationResponse<EnterpriseDTO>> updateEnterprise(
            @PathVariable Long id,
            @RequestBody EnterpriseDTO enterpriseDto) {

        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);

        enterpriseService.updateEnterprise(id, enterpriseToUpdate -> {
            if (enterprise.getNomeEmpresa() != null) {
                enterpriseToUpdate.setNomeEmpresa(enterprise.getNomeEmpresa());
            }
            if (enterprise.getCnpj() != null) {
                enterpriseToUpdate.setCnpj(enterprise.getCnpj());
            }
            if (enterprise.getSetorAtuacao() != null) {
                enterpriseToUpdate.setSetorAtuacao(enterprise.getSetorAtuacao());
            }
            if (enterprise.getReprentantName() != null) {
                enterpriseToUpdate.setReprentantName(enterprise.getReprentantName());
            }
            if (enterprise.getReprentantEmail() != null) {
                enterpriseToUpdate.setReprentantEmail(enterprise.getReprentantEmail());
            }
            if (enterprise.getReprentantPhone() != null) {
                enterpriseToUpdate.setReprentantPhone(enterprise.getReprentantPhone());
            }
            if (enterprise.getReprentantPosition() != null) {
                enterpriseToUpdate.setReprentantPosition(enterprise.getReprentantPosition());
            }
            if (enterprise.getPhone() != null) {
                enterpriseToUpdate.setPhone(enterprise.getPhone());
            }
        });

        Enterprise updatedEnterprise = enterpriseService.findById(id);
        EnterpriseDTO updatedEnterpriseDTO = enterpriseMapper.toDto(updatedEnterprise);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(updatedEnterpriseDTO));
    }

    @Tag(name = "Disable Enterprise")
    @PatchMapping("/{id}/enabled")
    @Operation(summary = "Enable or disable an enterprise by ID")
    public ResponseEntity<ApplicationResponse<String>> disableEnterprise(
            @PathVariable Long id,
            @RequestParam Boolean disable) {

        Enterprise updatedEnterprise = enterpriseService.disableEnterprise(id, disable);
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .body(ApplicationResponse.ofSuccess(updatedEnterprise.getEnabled().toString()));
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


}