package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.*;
import br.com.apihubinovacao.domain.usecases.opportunitybank.create.CreateOpportunityUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetAllOpportunitiesUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetApprovedActiveOpportunitiesUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetOpportunitiesByCompanyNameUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.update.UpdateOpportunityDetailsUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.update.UpdateOpportunityStatusUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opportunities")
public class OpportunityController {

    private final CreateOpportunityUseCase createOpportunityUseCase;
    private final GetAllOpportunitiesUseCase getAllOpportunitiesUseCase;
    private final GetOpportunitiesByCompanyNameUseCase getOpportunitiesByCompanyNameUseCase;
    private final UpdateOpportunityStatusUseCase updateOpportunityStatusUseCase;
    private final GetApprovedActiveOpportunitiesUseCase getApprovedActiveOpportunitiesUseCase;
    private final UpdateOpportunityDetailsUseCase updateOpportunityDetailsUseCase;

    @Autowired
    public OpportunityController(CreateOpportunityUseCase createOpportunityUseCase,
                                 GetAllOpportunitiesUseCase getAllOpportunitiesUseCase,
                                 GetOpportunitiesByCompanyNameUseCase getOpportunitiesByCompanyNameUseCase,
                                 UpdateOpportunityStatusUseCase updateOpportunityStatusUseCase, GetApprovedActiveOpportunitiesUseCase getApprovedActiveOpportunitiesUseCase, UpdateOpportunityDetailsUseCase updateOpportunityDetailsUseCase) {
        this.createOpportunityUseCase = createOpportunityUseCase;
        this.getAllOpportunitiesUseCase = getAllOpportunitiesUseCase;
        this.getOpportunitiesByCompanyNameUseCase = getOpportunitiesByCompanyNameUseCase;
        this.updateOpportunityStatusUseCase = updateOpportunityStatusUseCase;
        this.getApprovedActiveOpportunitiesUseCase = getApprovedActiveOpportunitiesUseCase;
        this.updateOpportunityDetailsUseCase = updateOpportunityDetailsUseCase;
    }

    // Endpoint para criar uma nova oportunidade
    @PostMapping("/create")
    public ResponseEntity<OpportunityResponseDTO> createOpportunity(@RequestBody OpportunityCreateDTO opportunityCreateDTO) {
        OpportunityResponseDTO createdOpportunity = createOpportunityUseCase.execute(opportunityCreateDTO);
        return ResponseEntity.ok(createdOpportunity);
    }

    // Endpoint para buscar todas as oportunidades
    @GetMapping("/all")
    public ResponseEntity<List<OpportunityResponseDTO>> getAllOpportunities() {
        List<OpportunityResponseDTO> opportunities = getAllOpportunitiesUseCase.execute();
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/approved/active")
    public ResponseEntity<List<OpportunityResponseDTO>> getApprovedActiveOpportunities() {
        List<OpportunityResponseDTO> opportunities = getApprovedActiveOpportunitiesUseCase.execute();
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<OpportunityResponseDTO>> getOpportunitiesByCompanyName(@PathVariable String companyName) {
        List<OpportunityResponseDTO> opportunities = getOpportunitiesByCompanyNameUseCase.execute(companyName);
        return ResponseEntity.ok(opportunities);
    }

    @PutMapping("/{opportunityId}/status")
    public ResponseEntity<OpportunityUpdateStatusResponseDTO> updateOpportunityStatus(
            @PathVariable Long opportunityId,
            @RequestBody OpportunityUpdateStatusDTO opportunityUpdateStatusDTO) {

        OpportunityUpdateStatusResponseDTO updatedOpportunity = updateOpportunityStatusUseCase.execute(opportunityId, opportunityUpdateStatusDTO);
        return ResponseEntity.ok(updatedOpportunity);
    }

    @PutMapping("/{opportunityId}/details")
    public ResponseEntity<Void> updateOpportunityDetails(
            @PathVariable Long opportunityId,
            @RequestBody UpdateOpportunityDetailsDTO updateDTO
    ){
       updateOpportunityDetailsUseCase.execute(opportunityId, updateDTO);
        return ResponseEntity.noContent().build();
    }
}