package br.com.apihubinovacao.domain.usecases.opportunitybank.get;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetOpportunitiesByCompanyNameUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public Page<OpportunityResponseDTO> execute(String companyName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OpportunitiesBank> opportunitiesPage = opportunitiesBankRepository.findByPartnerCompanyName(companyName, pageable);

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
                opportunity.getFeedback(),
                opportunity.getJustification(),
                opportunity.getIdManager()
        ));
    }
}