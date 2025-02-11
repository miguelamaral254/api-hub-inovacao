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
        Pageable pageable = PageRequest.of(page, size);
        Page<OpportunitiesBank> opportunitiesPage = opportunitiesBankRepository
                .findByStatusAndFlagActive(StatusSolicitation.APROVADA, true, pageable);

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
                opportunity.getPartnerCompany().getInstitutionOrganization()
        ));
    }
}