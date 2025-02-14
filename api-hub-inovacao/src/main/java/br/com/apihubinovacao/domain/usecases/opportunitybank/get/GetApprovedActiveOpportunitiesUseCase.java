package br.com.apihubinovacao.domain.usecases.opportunitybank.get;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetApprovedActiveOpportunitiesUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public Page<OpportunityResponseDTO> execute(int page, int size) {
        // Define a páginação com os parâmetros recebidos
        Pageable pageable = PageRequest.of(page, size);

        // Recupera oportunidades com status "APROVADA" e "flagActive" como verdadeiro
        Page<OpportunitiesBank> opportunitiesPage = opportunitiesBankRepository
                .findByStatusAndFlagActive(StatusSolicitation.APROVADA, true, pageable);

        // Mapeia as oportunidades para o DTO, incluindo feedback, justification e idManager
        return opportunitiesPage.map(opportunity -> new OpportunityResponseDTO(
                opportunity.getId(),
                opportunity.getTitle(),
                opportunity.getDescription(),
                opportunity.getUrlPhoto(),
                opportunity.getPdfLink(),
                opportunity.getSiteLink(),
                opportunity.getTypeBO(),
                opportunity.getAuthorEmail(),
                opportunity.getStatus(),
                opportunity.getCreationDate(),
                opportunity.isFlagActive(),
                opportunity.getPartnerCompany().getId(),
                opportunity.getPartnerCompany().getInstitutionOrganization(),
                opportunity.getFeedback(),  // Inclui feedback
                opportunity.getJustification(),  // Inclui justificativa
                opportunity.getIdManager()  // Inclui idManager
        ));
    }
}