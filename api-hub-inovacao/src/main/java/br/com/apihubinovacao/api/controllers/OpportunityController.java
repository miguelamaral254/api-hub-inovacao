package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityCreateDTO;
import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.usecases.opportunitybank.create.CreateOpportunityUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetAllOpportunitiesUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetOpportunitiesByCompanyNameUseCase;
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

    @Autowired
    public OpportunityController(CreateOpportunityUseCase createOpportunityUseCase,
                                 GetAllOpportunitiesUseCase getAllOpportunitiesUseCase,
                                 GetOpportunitiesByCompanyNameUseCase getOpportunitiesByCompanyNameUseCase) {
        this.createOpportunityUseCase = createOpportunityUseCase;
        this.getAllOpportunitiesUseCase = getAllOpportunitiesUseCase;
        this.getOpportunitiesByCompanyNameUseCase = getOpportunitiesByCompanyNameUseCase;
    }

    // Endpoint para criar uma nova oportunidade
    @PostMapping("/create")
    public ResponseEntity<OpportunityResponseDTO> createOpportunity(@RequestBody OpportunityCreateDTO opportunityCreateDTO) {
        OpportunityResponseDTO createdOpportunity = createOpportunityUseCase.execute(opportunityCreateDTO);
        return ResponseEntity.ok(createdOpportunity);
    }
    @PreAuthorize("hasRole('PARTNER_COMPANY')")
    @GetMapping("/all")
    public ResponseEntity<List<OpportunityResponseDTO>> getAllOpportunities() {
        List<OpportunityResponseDTO> opportunities = getAllOpportunitiesUseCase.execute();
        return ResponseEntity.ok(opportunities);
    }

    // Endpoint para buscar oportunidades por nome da empresa
    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<OpportunityResponseDTO>> getOpportunitiesByCompanyName(@PathVariable String companyName) {
        List<OpportunityResponseDTO> opportunities = getOpportunitiesByCompanyNameUseCase.execute(companyName);
        return ResponseEntity.ok(opportunities);
    }
}