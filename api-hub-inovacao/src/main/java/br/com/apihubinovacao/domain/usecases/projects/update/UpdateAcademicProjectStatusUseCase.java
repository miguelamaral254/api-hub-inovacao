package br.com.apihubinovacao.domain.usecases.projects.update;

import br.com.apihubinovacao.domain.dtos.projects.UpdateAcademicProjectStatusDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAcademicProjectStatusUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public void execute(Long projectId, UpdateAcademicProjectStatusDTO dto) {
        Optional<AcademicProject> projectOpt = academicProjectRepository.findById(projectId);

        if (projectOpt.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PROJECT_NOT_FOUND);
        }

        AcademicProject project = projectOpt.get();

        project.setStatus(dto.status());
        project.setFeedback(dto.feedback());
        project.setJustification(dto.justification());
        project.setIdManager(dto.idManager());

        // Salva as alterações no repositório
        academicProjectRepository.save(project);
    }
}