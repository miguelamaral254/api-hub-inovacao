package br.com.apihubinovacao.domain.opportunity;

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
@RequestMapping("/opportunities")
@AllArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;
    private final OpportunityMapper opportunityMapper;

    @Tag(name = "Create Opportunity")
    @Operation(summary = "Create a new opportunity")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createOpportunity(
            @RequestPart("dto") OpportunityDTO opportunityDto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request) throws IOException {

        Opportunity opportunity = opportunityMapper.toEntity(opportunityDto);
        Opportunity savedOpportunity = opportunityService.createOpportunity(opportunity, file, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOpportunity.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search opportunity by ID")
    public ResponseEntity<ApplicationResponse<OpportunityDTO>> findOpportunityById(
            @PathVariable Long id
    ) {
        Opportunity opportunity = opportunityService.findById(id);
        OpportunityDTO opportunityDto = opportunityMapper.toDto(opportunity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(opportunityDto));
    }

    @Tag(name = "Search Opportunities with filter")
    @GetMapping
    @Operation(summary = "Search opportunities with filters or all opportunities")
    public ResponseEntity<ApplicationResponse<Page<OpportunityDTO>>> searchOpportunities(
            @RequestParam(value = "tituloDesafio", required = false) String tituloDesafio,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "idManager", required = false) Long idManager,
            @RequestParam(value = "enterpriseId", required = false) Long enterpriseId,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            Pageable pageable) {

        Specification<Opportunity> specification = Specification.where(null);

        if (tituloDesafio != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("tituloDesafio")), "%" + tituloDesafio.toLowerCase() + "%"));
        }

        if (status != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")), status.toLowerCase()));
        }

        if (idManager != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("idManager").get("id"), idManager));
        }

        if (enterpriseId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enterprise").get("id"), enterpriseId));
        }

        if (enabled != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enabled"), enabled));
        }

        Page<Opportunity> opportunityPage = opportunityService.searchOpportunities(specification, pageable);
        Page<OpportunityDTO> opportunityDTOPage = opportunityMapper.toDto(opportunityPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(opportunityDTOPage));
    }

    @Tag(name = "Update Opportunity")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing opportunity")
    public ResponseEntity<ApplicationResponse<OpportunityDTO>> updateOpportunity(
            @PathVariable Long id,
            @RequestBody OpportunityDTO opportunityDto) {

        Opportunity opportunity = opportunityMapper.toEntity(opportunityDto);

        opportunityService.updateOpportunity(id, opportunityToUpdate -> {
            if (opportunity.getTituloDesafio() != null) {
                opportunityToUpdate.setTituloDesafio(opportunity.getTituloDesafio());
            }
            if (opportunity.getAreaProblema() != null) {
                opportunityToUpdate.setAreaProblema(opportunity.getAreaProblema());
            }
            if (opportunity.getDescricaoProblema() != null) {
                opportunityToUpdate.setDescricaoProblema(opportunity.getDescricaoProblema());
            }
            if (opportunity.getImpactoProblema() != null) {
                opportunityToUpdate.setImpactoProblema(opportunity.getImpactoProblema());
            }
            if (opportunity.getSolucoesTestadas() != null) {
                opportunityToUpdate.setSolucoesTestadas(opportunity.getSolucoesTestadas());
            }
            if (opportunity.getExpectativas() != null) {
                opportunityToUpdate.setExpectativas(opportunity.getExpectativas());
            }
            if (opportunity.getRestricoes() != null) {
                opportunityToUpdate.setRestricoes(opportunity.getRestricoes());
            }
            if (opportunity.getDisponibilidadeDados() != null) {
                opportunityToUpdate.setDisponibilidadeDados(opportunity.getDisponibilidadeDados());
            }
            if (opportunity.isMentoriaSuporte()) {
                opportunityToUpdate.setMentoriaSuporte(opportunity.isMentoriaSuporte());
            }
            if (opportunity.isVisitasTecnicas()) {
                opportunityToUpdate.setVisitasTecnicas(opportunity.isVisitasTecnicas());
            }
            if (opportunity.isAutorizacao()) {
                opportunityToUpdate.setAutorizacao(opportunity.isAutorizacao());
            }
            if (opportunity.getStatus() != null) {
                opportunityToUpdate.setStatus(opportunity.getStatus());
            }
            if (opportunity.getFeedback() != null) {
                opportunityToUpdate.setFeedback(opportunity.getFeedback());
            }
            if (opportunity.getJustification() != null) {
                opportunityToUpdate.setJustification(opportunity.getJustification());
            }
        });

        Opportunity updatedOpportunity = opportunityService.findById(id);
        OpportunityDTO updatedOpportunityDTO = opportunityMapper.toDto(updatedOpportunity);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(updatedOpportunityDTO));
    }

    @Tag(name = "Update Opportunity Status")
    @Operation(summary = "Update the status of an opportunity")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse<String>> updateOpportunityStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        StatusSolicitation statusEnum = StatusSolicitation.valueOf(status.toUpperCase());

        Opportunity updatedOpportunity = opportunityService.updateStatus(id, statusEnum);

        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .body(ApplicationResponse.ofSuccess(updatedOpportunity.getStatus().name()));
    }

    @Tag(name = "Delete Opportunity")
    @Operation(summary = "Delete an opportunity by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}