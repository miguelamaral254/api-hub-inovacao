package br.com.apihubinovacao.domain.usecases.projects.update;

import br.com.apihubinovacao.domain.dtos.projects.UpdateAcademicProjectDetailsDTO;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAcademicProjectDetailsUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public void execute(Long projectId, UpdateAcademicProjectDetailsDTO updateDTO) {
        Optional<AcademicProject> projectOpt = academicProjectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PROJECT_NOT_FOUND);
        }

        AcademicProject project = projectOpt.get();

        if (updateDTO.title() != null) {
            project.setTitle(updateDTO.title());
        }
        if (updateDTO.description() != null) {
            project.setDescription(updateDTO.description());
        }
        if (updateDTO.urlPhoto() != null) {
            project.setUrlPhoto(updateDTO.urlPhoto());
        }
        if (updateDTO.pdfLink() != null) {
            project.setPdfLink(updateDTO.pdfLink());
        }
        if (updateDTO.siteLink() != null) {
            project.setSiteLink(updateDTO.siteLink());
        }


        academicProjectRepository.save(project);
    }
}