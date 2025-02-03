package br.com.apihubinovacao.domain.usecases.opportunitybank.update;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.UpdateOpportunityDetailsDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateOpportunityDetailsUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    public void execute(Long opportunityId, UpdateOpportunityDetailsDTO updateDetailsDTO) {
        Optional<OpportunitiesBank> opportunitiesBankOpto = opportunitiesBankRepository.findById(opportunityId);

        if (opportunitiesBankOpto.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.OPPORTUNITY_NOT_FOUND);
        }

        OpportunitiesBank opportunities = opportunitiesBankOpto.get();

        if (updateDetailsDTO.title() != null){
            opportunities.setTitle(updateDetailsDTO.title());
        }

        if (updateDetailsDTO.description() != null){
            opportunities.setDescription(updateDetailsDTO.description());
        }

        if (updateDetailsDTO.urlPhoto() != null){
            opportunities.setUrlPhoto(updateDetailsDTO.urlPhoto());
        }

        if (updateDetailsDTO.pdfLink() != null){
            opportunities.setPdfLink(updateDetailsDTO.pdfLink());
        }

        if (updateDetailsDTO.siteLink() != null){
            opportunities.setSiteLink(updateDetailsDTO.siteLink());
        }
        opportunitiesBankRepository.save(opportunities);

    }
}
