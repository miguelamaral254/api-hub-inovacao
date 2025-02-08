package br.com.apihubinovacao.domain.usecases.opportunitybank.create;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityCreateDTO;
import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.models.users.PartnerCompany;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import br.com.apihubinovacao.domain.repositories.PartnerCompanyRepository;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
@Service
public class CreateOpportunityUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    @Autowired
    private PartnerCompanyRepository partnerCompanyRepository;

    @Autowired
    private ImageService imageService; // Injetando o ImageService

    public OpportunityResponseDTO execute(OpportunityCreateDTO opportunityCreateDTO) {
        try {
            OpportunitiesBank opportunity = new OpportunitiesBank();
            opportunity.setTitle(opportunityCreateDTO.title());
            opportunity.setDescription(opportunityCreateDTO.description());

            // Agora, `urlPhoto` já contém o caminho correto salvo no controlador
            opportunity.setUrlPhoto(opportunityCreateDTO.urlPhoto());

            opportunity.setPdfLink(opportunityCreateDTO.pdfLink());
            opportunity.setSiteLink(opportunityCreateDTO.siteLink());
            opportunity.setTypeBO(opportunityCreateDTO.typeBO());
            opportunity.setAuthorEmail(opportunityCreateDTO.authorEmail());
            opportunity.setStatus(opportunityCreateDTO.status() != null ? opportunityCreateDTO.status() : StatusSolicitation.PENDENTE);
            opportunity.setFlagActive(opportunityCreateDTO.flagActive());
            opportunity.setCreationDate(LocalDate.now());

            PartnerCompany partnerCompany = partnerCompanyRepository.findById(opportunityCreateDTO.partnerCompanyId())
                    .orElseThrow(() -> new BusinessException(ErrorCodeEnum.PARTNER_COMPANY_NOT_FOUND));

            if (!partnerCompany.getEmail().equals(opportunityCreateDTO.authorEmail())) {
                throw new BusinessException(ErrorCodeEnum.EMAIL_DOES_NOT_MATCH);
            }

            opportunity.setPartnerCompany(partnerCompany);
            OpportunitiesBank savedOpportunity = opportunitiesBankRepository.save(opportunity);

            return mapToOpportunityDTO(savedOpportunity);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.OPPORTUNITY_CREATION_FAILED);
        }
    }
    private OpportunityResponseDTO mapToOpportunityDTO(OpportunitiesBank savedOpportunity) {
        return new OpportunityResponseDTO(
                savedOpportunity.getId(),
                savedOpportunity.getTitle(),
                savedOpportunity.getDescription(),
                savedOpportunity.getUrlPhoto(),
                savedOpportunity.getPdfLink(),
                savedOpportunity.getSiteLink(),
                savedOpportunity.getTypeBO(),
                savedOpportunity.getAuthorEmail(),
                savedOpportunity.getStatus(),
                savedOpportunity.getCreationDate(),
                savedOpportunity.isFlagActive(),
                savedOpportunity.getPartnerCompany().getId(),
                savedOpportunity.getPartnerCompany().getInstitutionOrganization()
        );
    }
}