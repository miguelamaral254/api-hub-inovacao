package br.com.apihubinovacao.domain.usecases.opportunitybank.update;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityUpdateStatusResponseDTO;
import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityUpdateStatusDTO;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UpdateOpportunityStatusUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public OpportunityUpdateStatusResponseDTO execute(Long id, OpportunityUpdateStatusDTO opportunityUpdateStatusDTO) {
        try {
            OpportunitiesBank opportunity = opportunitiesBankRepository.findById(id)
                    .orElseThrow(() -> new BusinessException(ErrorCodeEnum.OPPORTUNITY_NOT_FOUND));

            opportunity.setStatus(opportunityUpdateStatusDTO.status());
            opportunity.setValidationDate(opportunityUpdateStatusDTO.validationDate());
            opportunity.setFeedback(opportunityUpdateStatusDTO.feedback());
            opportunity.setJustification(opportunityUpdateStatusDTO.justification());
            opportunity.setIdManager(opportunityUpdateStatusDTO.idManager());

            OpportunitiesBank updatedOpportunity = opportunitiesBankRepository.save(opportunity);

            return mapToOpportunityUpdateStatusResponseDTO(updatedOpportunity);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.OPPORTUNITY_UPDATE_FAILED);
        }
    }

    private OpportunityUpdateStatusResponseDTO mapToOpportunityUpdateStatusResponseDTO(OpportunitiesBank updatedOpportunity) {
        return new OpportunityUpdateStatusResponseDTO(
                updatedOpportunity.getId(),
                updatedOpportunity.getTitle(),
                updatedOpportunity.getDescription(),
                updatedOpportunity.getUrlPhoto(),
                updatedOpportunity.getPdfLink(),
                updatedOpportunity.getSiteLink(),
                updatedOpportunity.getTypeBO(),
                updatedOpportunity.getAuthorEmail(),
                updatedOpportunity.getStatus(),
                updatedOpportunity.getCreationDate(),
                updatedOpportunity.isFlagActive(),
                updatedOpportunity.getPartnerCompany().getId(),
                updatedOpportunity.getValidationDate(),
                updatedOpportunity.getFeedback(),
                updatedOpportunity.getJustification(),
                updatedOpportunity.getIdManager()
        );
    }
}