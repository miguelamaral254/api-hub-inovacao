package br.com.apihubinovacao.domain.usecases.projects.create;

import br.com.apihubinovacao.domain.dtos.AcademicProjectCreateDTO;
import br.com.apihubinovacao.domain.dtos.AcademicProjectResponseDTO;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateAcademicProjectUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public AcademicProjectResponseDTO execute(AcademicProjectCreateDTO createDTO) {

        // Validação simples de campos obrigatórios
        if (createDTO.title() == null || createDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (createDTO.description() == null || createDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        // Verificar se o projeto com o mesmo título já existe
        boolean projectExists = academicProjectRepository.existsByTitle(createDTO.title());
        if (projectExists) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_USER); // Usar erro apropriado
        }

        // Criação do modelo do projeto acadêmico
        AcademicProject project = new AcademicProject();
        project.setTitle(createDTO.title());
        project.setDescription(createDTO.description());
        project.setUrlPhoto(createDTO.urlPhoto());
        project.setPdfLink(createDTO.pdfLink());
        project.setSiteLink(createDTO.siteLink());
        project.setTypeAP(createDTO.typeAP());
        project.setAuthorEmail(createDTO.userEmail());
        project.setCreationDate(LocalDate.now());

        // Salvar o projeto na base de dados
        AcademicProject savedProject = academicProjectRepository.save(project);

        // Mapear para o DTO de resposta
        return new AcademicProjectResponseDTO(
                savedProject.getId(),
                savedProject.getTitle(),
                savedProject.getDescription(),
                savedProject.getUrlPhoto(),
                savedProject.getPdfLink(),
                savedProject.getSiteLink(),
                savedProject.getTypeAP(),
                savedProject.getAuthorEmail(),
                savedProject.getCreationDate().toString()
        );
    }
}