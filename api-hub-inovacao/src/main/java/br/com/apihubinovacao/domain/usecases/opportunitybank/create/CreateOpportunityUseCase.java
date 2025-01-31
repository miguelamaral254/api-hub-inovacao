package br.com.apihubinovacao.domain.usecases.opportunitybank.create;

import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityCreateDTO;
import br.com.apihubinovacao.domain.dtos.OpportunityBank.OpportunityResponseDTO;
import br.com.apihubinovacao.domain.models.projects.OpportunitiesBank;
import br.com.apihubinovacao.domain.models.users.PartnerCompany;
import br.com.apihubinovacao.domain.repositories.OpportunitiesBankRepository;
import br.com.apihubinovacao.domain.repositories.PartnerCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateOpportunityUseCase {

    @Autowired
    private OpportunitiesBankRepository opportunitiesBankRepository;

    @Autowired
    private PartnerCompanyRepository partnerCompanyRepository;

    public OpportunityResponseDTO execute(OpportunityCreateDTO opportunityCreateDTO) {
        // Criação da instância da oportunidade
        OpportunitiesBank opportunity = new OpportunitiesBank();

        // Definindo os valores a partir do DTO
        opportunity.setTitle(opportunityCreateDTO.title());
        opportunity.setDescription(opportunityCreateDTO.description());
        opportunity.setUrlPhoto(opportunityCreateDTO.urlPhoto());
        opportunity.setPdfLink(opportunityCreateDTO.pdfLink());
        opportunity.setSiteLink(opportunityCreateDTO.siteLink());
        opportunity.setAuthorEmail(opportunityCreateDTO.authorEmail());
        opportunity.setStatus(opportunityCreateDTO.status());
        opportunity.setFlagActive(opportunityCreateDTO.flagActive());

        // Definindo a data de criação (sem hora)
        opportunity.setCreationDate(LocalDate.now());  // Aqui usamos LocalDate, sem hora

        // Buscando a empresa parceira pelo ID
        PartnerCompany partnerCompany = partnerCompanyRepository.findById(opportunityCreateDTO.partnerCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa parceira não encontrada"));

        // Atribuindo a empresa parceira à oportunidade
        opportunity.setPartnerCompany(partnerCompany);

        // Salvando a oportunidade no banco de dados
        OpportunitiesBank savedOpportunity = opportunitiesBankRepository.save(opportunity);

        // Retornando a resposta com os dados da oportunidade salva
        return mapToOpportunityResponseDTO(savedOpportunity);
    }

    // Método para mapear a entidade OpportunitiesBank para OpportunityResponseDTO
    private OpportunityResponseDTO mapToOpportunityResponseDTO(OpportunitiesBank savedOpportunity) {
        return new OpportunityResponseDTO(
                savedOpportunity.getId(),
                savedOpportunity.getTitle(),
                savedOpportunity.getDescription(),
                savedOpportunity.getUrlPhoto(),
                savedOpportunity.getPdfLink(),
                savedOpportunity.getSiteLink(),
                savedOpportunity.getAuthorEmail(),
                savedOpportunity.getStatus(),
                savedOpportunity.getCreationDate(),
                savedOpportunity.isFlagActive(),
                savedOpportunity.getPartnerCompany().getId(),
                savedOpportunity.getValidationDate(),
                savedOpportunity.getFeedback(),
                savedOpportunity.getJustification(),
                savedOpportunity.getIdManager()
        );
    }
}