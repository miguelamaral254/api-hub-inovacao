package br.com.apihubinovacao.domain.usecases.opportunitybank.get;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListAllOpportunitiesForManagerUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public Page<OpportunityResponseDTO> execute(Long idManager, Pageable pageable) {
        if (idManager == null) {
            throw new BusinessException(ErrorCodeEnum.MANAGER_NOT_FOUND);
        }

        Page<OpportunitiesBank> allOpportunities = opportunitiesBankRepository.findByIdManager(idManager, pageable);

        if (allOpportunities.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.OPPORTUNITY_NOT_FOUND);
        }

        return allOpportunities.map(opportunity -> {
            try {
                return new OpportunityResponseDTO(
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
                );
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.INVALID_OPPORTUNITY_DATA);
            }
        });
    }
}