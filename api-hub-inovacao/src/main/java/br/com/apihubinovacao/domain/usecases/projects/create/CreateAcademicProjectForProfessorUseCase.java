package br.com.apihubinovacao.domain.usecases.projects.create;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectCreateProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.projects.Coauthor;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateAcademicProjectForProfessorUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public AcademicProjectResponseProfessorDTO execute(AcademicProjectCreateProfessorDTO createDTO, MultipartFile imageFile, HttpServletRequest request) {
        validateCreateDTO(createDTO);

        AcademicProject project = new AcademicProject();
        project.setTitle(createDTO.title());
        project.setDescription(createDTO.description());
        project.setPdfLink(createDTO.pdfLink());
        project.setSiteLink(createDTO.siteLink());
        project.setTypeAP(createDTO.typeAP());
        project.setAuthorEmail(createDTO.userEmail());
        project.setStatus(createDTO.status());
        project.setCreationDate(LocalDate.now());
        project.setFeedback(null);
        project.setJustification(null);
        project.setIdManager(null);

        project.setProfessor(professorRepository.findById(createDTO.professorId())
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

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = imageService.saveImage(imageFile, request);
                project.setUrlPhoto(imageUrl);
            } catch (IOException e) {
                throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILED);
            }
        }

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

    private void validateCreateDTO(AcademicProjectCreateProfessorDTO createDTO) {
        if (createDTO.title() == null || createDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
        if (createDTO.description() == null || createDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
    }
}