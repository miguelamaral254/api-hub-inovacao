package br.com.apihubinovacao.domain.usecases.projects.create;

import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectCreateProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateAcademicProjectForProfessorUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public AcademicProjectResponseProfessorDTO execute(AcademicProjectCreateProfessorDTO createDTO) {
        validateCreateDTO(createDTO);

        AcademicProject project = new AcademicProject();
        project.setTitle(createDTO.title());
        project.setDescription(createDTO.description());
        project.setUrlPhoto(createDTO.urlPhoto());
        project.setPdfLink(createDTO.pdfLink());
        project.setSiteLink(createDTO.siteLink());
        project.setTypeAP(createDTO.typeAP());
        project.setAuthorEmail(createDTO.userEmail());
        project.setStatus(createDTO.status());
        project.setCreationDate(LocalDate.now());

        // Inicializa os novos campos como null (ou com valores padrão, se necessário)
        project.setFeedback(null);
        project.setJustification(null);
        project.setIdManager(null);

        project.setProfessor(professorRepository.findById(createDTO.professorId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USER_NOT_FOUND)));

        AcademicProject savedProject = academicProjectRepository.save(project);

        return new AcademicProjectResponseProfessorDTO(
                savedProject.getId(),
                savedProject.getTitle(),
                savedProject.getDescription(),
                savedProject.getUrlPhoto(),
                savedProject.getPdfLink(),
                savedProject.getSiteLink(),
                savedProject.getTypeAP(),
                savedProject.getAuthorEmail(),
                savedProject.getCreationDate().toString(),
                savedProject.getStatus(),
                savedProject.getProfessor().getId(),
                savedProject.getProfessor().getName(),
                savedProject.getFeedback(),       // Novo campo
                savedProject.getJustification(),  // Novo campo
                savedProject.getIdManager()       // Novo campo
        );
    }

    private void validateCreateDTO(AcademicProjectCreateProfessorDTO createDTO) {
        if (createDTO.title() == null || createDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
        if (createDTO.description() == null || createDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
    }
}