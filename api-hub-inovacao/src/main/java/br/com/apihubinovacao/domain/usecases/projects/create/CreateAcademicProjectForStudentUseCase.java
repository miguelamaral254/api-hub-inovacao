package br.com.apihubinovacao.domain.usecases.projects.create;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.projects.Coauthor;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateAcademicProjectForStudentUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    public AcademicProjectResponseStudentDTO execute(AcademicProjectCreateStudentDTO createDTO) {
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

        project.setStudent(studentRepository.findById(createDTO.studentId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USER_NOT_FOUND)));
        if (createDTO.coauthors() != null) {
            List<Coauthor> coauthors = createDTO.coauthors().stream()
                    .map(coauthorDTO -> {
                        Coauthor coauthor = new Coauthor();
                        coauthor.setName(coauthorDTO.name());
                        coauthor.setEmail(coauthorDTO.email());
                        coauthor.setPhone(coauthorDTO.phone());
                        coauthor.setAcademicProject(project);
                        return coauthor;
                    })
                    .collect(Collectors.toList());
            project.setCoauthors(coauthors);
        }

        AcademicProject savedProject = academicProjectRepository.save(project);

        return new AcademicProjectResponseStudentDTO(
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
                savedProject.getStudent().getId(),
                savedProject.getStudent().getName(),
                savedProject.getFeedback(),
                savedProject.getJustification(),
                savedProject.getIdManager(),
                savedProject.getCoauthors() != null ? savedProject.getCoauthors().stream()
                        .map(coauthor -> new CoauthorDTO(
                                coauthor.getName(),
                                coauthor.getEmail(),
                                coauthor.getPhone()
                        ))
                        .collect(Collectors.toList()) : null
        );
    }

    private void validateCreateDTO(AcademicProjectCreateStudentDTO createDTO) {
        if (createDTO.title() == null || createDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
        if (createDTO.description() == null || createDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
    }
}