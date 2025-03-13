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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


@Service
public class CreateOpportunityUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    @Autowired
    private PartnerCompanyRepository partnerCompanyRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public OpportunityResponseDTO execute(OpportunityCreateDTO opportunityCreateDTO, MultipartFile imageFile, HttpServletRequest request) {
        validateOpportunity(opportunityCreateDTO);

        PartnerCompany partnerCompany = partnerCompanyRepository.findById(opportunityCreateDTO.partnerCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.PARTNER_COMPANY_NOT_FOUND));

        if (!partnerCompany.getEmail().equals(opportunityCreateDTO.authorEmail())) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_DOES_NOT_MATCH);
        }

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 5 * 1024 * 1024) {
                throw new BusinessException(ErrorCodeEnum.IMAGE_SIZE_EXCEEDED);
            }
            try {
                imageUrl = imageService.saveImage(imageFile, request);
            } catch (IOException e) {
                throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILED);
            }
        }

        OpportunitiesBank opportunity = new OpportunitiesBank();
        opportunity.setTitle(opportunityCreateDTO.title());
        opportunity.setDescription(opportunityCreateDTO.description());
        opportunity.setUrlPhoto(imageUrl);
        opportunity.setPdfLink(opportunityCreateDTO.pdfLink());
        opportunity.setSiteLink(opportunityCreateDTO.siteLink());
        opportunity.setTypeBO(opportunityCreateDTO.typeBO());
        opportunity.setAuthorEmail(opportunityCreateDTO.authorEmail());
        opportunity.setStatus(opportunityCreateDTO.status() != null ? opportunityCreateDTO.status() : StatusSolicitation.PENDENTE);
        opportunity.setFlagActive(opportunityCreateDTO.flagActive());
        opportunity.setCreationDate(LocalDate.now());
        opportunity.setPartnerCompany(partnerCompany);

        // Atribuindo valores para os campos adicionais (exemplo)
        opportunity.setFeedback(opportunityCreateDTO.feedback()); // Se disponível no DTO
        opportunity.setJustification(opportunityCreateDTO.justification()); // Se disponível no DTO
        opportunity.setIdManager(opportunityCreateDTO.idManager()); // Se disponível no DTO

        OpportunitiesBank savedOpportunity = opportunitiesBankRepository.save(opportunity);
        return mapToOpportunityDTO(savedOpportunity);
    }

    private void validateOpportunity(OpportunityCreateDTO opportunityCreateDTO) {
        if (opportunityCreateDTO == null) {
            throw new BusinessException(ErrorCodeEnum.INVALID_OPPORTUNITY_DATA);
        }
        if (opportunityCreateDTO.title() == null || opportunityCreateDTO.title().trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_OPPORTUNITY_TITLE);
        }
        if (opportunityCreateDTO.description() == null || opportunityCreateDTO.description().trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_OPPORTUNITY_DESCRIPTION);
        }
        if (opportunityCreateDTO.partnerCompanyId() == null) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PARTNER_COMPANY);
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
                savedOpportunity.getPartnerCompany().getInstitutionOrganization(),
                savedOpportunity.getFeedback(), // Feedback
                savedOpportunity.getJustification(), // Justification
                savedOpportunity.getIdManager() // idManager
        );
    }
}