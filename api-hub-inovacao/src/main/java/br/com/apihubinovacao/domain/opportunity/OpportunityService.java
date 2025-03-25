package br.com.apihubinovacao.domain.opportunity;

import br.com.apihubinovacao.core.BusinessException;
import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.users.UserRepository;
import br.com.apihubinovacao.infrastructure.conf.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public Opportunity createOpportunity(Opportunity opportunity, MultipartFile file, HttpServletRequest request) {
        validateImageCreateRules(opportunity, file, request);
        return opportunityRepository.save(opportunity);
    }

    private void validateBusinessRules(Opportunity opportunity) {
        if (opportunity.getTituloDesafio() == null || opportunity.getTituloDesafio().isEmpty()) {
            throw new BusinessException(OpportunityExceptionCodeEnum.INVALID_OPPORTUNITY_TITLE);
        }

        if (opportunity.getEnterprise() == null) {
            throw new BusinessException(OpportunityExceptionCodeEnum.INVALID_OPPORTUNITY_DATA);
        }

        if (opportunity.getIdManager() == null || !userRepository.existsById(opportunity.getIdManager().getId())) {
            throw new BusinessException(OpportunityExceptionCodeEnum.MANAGER_NOT_FOUND);
        }

        if (opportunity.getStatus() == null) {
            throw new BusinessException(OpportunityExceptionCodeEnum.INVALID_OPPORTUNITY_STATUS);
        }
    }

    @Transactional(readOnly = true)
    public Page<Opportunity> searchOpportunities(Specification<Opportunity> specification, Pageable pageable) {
        return opportunityRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Opportunity findById(Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new BusinessException(OpportunityExceptionCodeEnum.OPPORTUNITY_NOT_FOUND));
    }

    @Transactional
    public void deleteOpportunity(Long id) {
        Opportunity opportunity = findById(id);
        opportunityRepository.delete(opportunity);
    }

    @Transactional
    public Opportunity updateStatus(Long id, StatusSolicitation newStatus) {
        Opportunity opportunity = findById(id);
        opportunity.setStatus(newStatus);
        return opportunityRepository.save(opportunity);
    }

    @Transactional
    public Opportunity updateOpportunity(Long id, Consumer<Opportunity> updateConsumer) {
        Opportunity existingOpportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new BusinessException(OpportunityExceptionCodeEnum.OPPORTUNITY_NOT_FOUND));

        updateConsumer.accept(existingOpportunity);

        validateUpdateBusiness(id, existingOpportunity);

        return opportunityRepository.save(existingOpportunity);
    }

    private void validateUpdateBusiness(Long id, Opportunity existingOpportunity) {
        if (existingOpportunity.getTituloDesafio() == null || existingOpportunity.getTituloDesafio().isEmpty()) {
            throw new BusinessException(OpportunityExceptionCodeEnum.INVALID_OPPORTUNITY_TITLE);
        }

        if (existingOpportunity.getStatus() == null) {
            throw new BusinessException(OpportunityExceptionCodeEnum.INVALID_OPPORTUNITY_STATUS);
        }
    }

    private void validateImageCreateRules(Opportunity opportunity, MultipartFile file, HttpServletRequest request) {
        try {
            validateBusinessRules(opportunity);

            if (file != null && !file.isEmpty()) {
                String imageUrl = imageService.saveImage(file, request);
                opportunity.setUrlPhoto(imageUrl);
            }

        } catch (IOException e) {
            throw new BusinessException(OpportunityExceptionCodeEnum.IMAGE_CREATION_FAILED);
        }
    }
}