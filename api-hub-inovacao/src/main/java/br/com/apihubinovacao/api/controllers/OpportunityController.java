package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.*;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeBO;
import br.com.apihubinovacao.domain.services.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final ImageService imageService;

    @Autowired
    public OpportunityController(CreateOpportunityUseCase createOpportunityUseCase,
                                 GetAllOpportunitiesUseCase getAllOpportunitiesUseCase,
                                 GetOpportunitiesByCompanyNameUseCase getOpportunitiesByCompanyNameUseCase,
                                 UpdateOpportunityStatusUseCase updateOpportunityStatusUseCase, GetApprovedActiveOpportunitiesUseCase getApprovedActiveOpportunitiesUseCase, UpdateOpportunityDetailsUseCase updateOpportunityDetailsUseCase, ImageService imageService) {
        this.createOpportunityUseCase = createOpportunityUseCase;
        this.getAllOpportunitiesUseCase = getAllOpportunitiesUseCase;
        this.getOpportunitiesByCompanyNameUseCase = getOpportunitiesByCompanyNameUseCase;
        this.updateOpportunityStatusUseCase = updateOpportunityStatusUseCase;
        this.getApprovedActiveOpportunitiesUseCase = getApprovedActiveOpportunitiesUseCase;
        this.updateOpportunityDetailsUseCase = updateOpportunityDetailsUseCase;
        this.imageService = imageService;
    }



    @PostMapping("/create")
    public ResponseEntity<OpportunityResponseDTO> createOpportunity(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("urlPhoto") MultipartFile file,  // Arquivo da imagem
            @RequestParam("pdfLink") String pdfLink,
            @RequestParam("siteLink") String siteLink,
            @RequestParam("typeBO") String typeBO,
            @RequestParam("authorEmail") String authorEmail,
            @RequestParam("status") String status,
            @RequestParam("flagActive") boolean flagActive,
            @RequestParam("partnerCompanyId") long partnerCompanyId) {

        // Salvar a imagem primeiro e obter o caminho dela
        String imagePath;
        try {
            imagePath = imageService.saveImage(file);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);  // Retorna erro caso o upload falhe
        }

        // Criar DTO com o caminho da imagem salva
        OpportunityCreateDTO opportunity = new OpportunityCreateDTO(
                title,
                description,
                imagePath,  // Caminho da imagem salva
                pdfLink,
                siteLink,
                TypeBO.valueOf(typeBO),  // Converter string para enum
                authorEmail,
                StatusSolicitation.valueOf(status),  // Converter string para enum
                flagActive,
                partnerCompanyId
        );

        OpportunityResponseDTO createdOpportunity = createOpportunityUseCase.execute(opportunity);
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