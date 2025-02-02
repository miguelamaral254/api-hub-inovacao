package br.com.apihubinovacao.domain.usecases.opportunitybank.get;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetApprovedActiveOpportunitiesUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public List<OpportunityResponseDTO> execute() {
        List<OpportunitiesBank> opportunitiesList = opportunitiesBankRepository
                .findByStatusAndFlagActive(StatusSolicitation.APROVADA, true);

        return opportunitiesList.stream()
                .map(opportunity -> new OpportunityResponseDTO(
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
                        opportunity.getPartnerCompany().getInstitutionOrganization() // Pegando o institutionOrganization

                ))
                .collect(Collectors.toList());
    }
}