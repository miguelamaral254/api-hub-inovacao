package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.*;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeBO;
import br.com.apihubinovacao.domain.services.ImageService;
import br.com.apihubinovacao.domain.usecases.opportunitybank.create.CreateOpportunityUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetAllOpportunitiesUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetApprovedActiveOpportunitiesUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.GetOpportunitiesByCompanyNameUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.get.ListAllOpportunitiesForManagerUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.update.UpdateOpportunityDetailsUseCase;
import br.com.apihubinovacao.domain.usecases.opportunitybank.update.UpdateOpportunityStatusUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final ListAllOpportunitiesForManagerUseCase listAllOpportunitiesForManagerUseCase;

    @Autowired
    public OpportunityController(CreateOpportunityUseCase createOpportunityUseCase,
                                 GetAllOpportunitiesUseCase getAllOpportunitiesUseCase,
                                 GetOpportunitiesByCompanyNameUseCase getOpportunitiesByCompanyNameUseCase,
                                 UpdateOpportunityStatusUseCase updateOpportunityStatusUseCase, GetApprovedActiveOpportunitiesUseCase getApprovedActiveOpportunitiesUseCase, UpdateOpportunityDetailsUseCase updateOpportunityDetailsUseCase, ImageService imageService, ListAllOpportunitiesForManagerUseCase listAllOpportunitiesForManagerUseCase) {
        this.createOpportunityUseCase = createOpportunityUseCase;
        this.getAllOpportunitiesUseCase = getAllOpportunitiesUseCase;
        this.getOpportunitiesByCompanyNameUseCase = getOpportunitiesByCompanyNameUseCase;
        this.updateOpportunityStatusUseCase = updateOpportunityStatusUseCase;
        this.getApprovedActiveOpportunitiesUseCase = getApprovedActiveOpportunitiesUseCase;
        this.updateOpportunityDetailsUseCase = updateOpportunityDetailsUseCase;
        this.imageService = imageService;
        this.listAllOpportunitiesForManagerUseCase = listAllOpportunitiesForManagerUseCase;
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<OpportunityResponseDTO> createOpportunity(
            @RequestPart("dto") OpportunityCreateDTO opportunityCreateDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            HttpServletRequest request) {

        OpportunityResponseDTO createdOpportunity = createOpportunityUseCase.execute(opportunityCreateDTO, imageFile, request);
        return ResponseEntity.ok(createdOpportunity);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<OpportunityResponseDTO>> getAllOpportunities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OpportunityResponseDTO> opportunities = getAllOpportunitiesUseCase.execute(page, size);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/approved/active")
    public ResponseEntity<Page<OpportunityResponseDTO>> getApprovedActiveOpportunities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OpportunityResponseDTO> opportunities = getApprovedActiveOpportunitiesUseCase.execute(page, size);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<Page<OpportunityResponseDTO>> getOpportunitiesByCompanyName(
            @PathVariable String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OpportunityResponseDTO> opportunities = getOpportunitiesByCompanyNameUseCase.execute(companyName, page, size);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("manager/all")
    public ResponseEntity<Page<OpportunityResponseDTO>> getAllOpportunitiesForManager(
            @RequestParam Long idManager, // Recebendo idManager diretamente na URL
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Passando o idManager para o UseCase
        Page<OpportunityResponseDTO> opportunities = listAllOpportunitiesForManagerUseCase.execute(idManager, pageable);

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