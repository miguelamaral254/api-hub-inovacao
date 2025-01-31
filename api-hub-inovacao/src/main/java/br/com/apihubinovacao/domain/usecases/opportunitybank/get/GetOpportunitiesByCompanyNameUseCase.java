package br.com.apihubinovacao.domain.usecases.opportunitybank.get;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOpportunitiesByCompanyNameUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public List<OpportunityResponseDTO> execute(String companyName) {
        List<OpportunitiesBank> opportunitiesList = opportunitiesBankRepository.findByPartnerCompanyName(companyName);

        return opportunitiesList.stream()
                .map(opportunity -> new OpportunityResponseDTO(
                        opportunity.getId(),
                        opportunity.getTitle(),
                        opportunity.getDescription(),
                        opportunity.getUrlPhoto(),
                        opportunity.getPdfLink(),
                        opportunity.getSiteLink(),
                        opportunity.getAuthorEmail(),
                        opportunity.getStatus(),
                        opportunity.getCreationDate(),
                        opportunity.isFlagActive(),
                        opportunity.getPartnerCompany().getId(),
                        opportunity.getValidationDate(),
                        opportunity.getFeedback(),
                        opportunity.getJustification(),
                        opportunity.getIdManager()
                ))
                .collect(Collectors.toList());
    }
}