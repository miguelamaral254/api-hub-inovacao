package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.authentication.AuthExceptionCodeEnum;
import br.com.apihubinovacao.core.BusinessException;
import br.com.apihubinovacao.domain.users.UserExceptionCodeEnum;
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

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public Projects createProject(Projects project, MultipartFile file, HttpServletRequest request) {
        validateImageCreateRules(project, file, request);
        return projectRepository.save(project);
    }


    @Transactional(readOnly = true)
    public Page<Projects> searchProjects(Specification<Projects> specification, Pageable pageable) {
        return projectRepository.findAll(specification, pageable);
    }



    @Transactional(readOnly = true)
    public Projects findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProjectExceptionCodeEnum.PROJECT_NOT_FOUND));
    }

    @Transactional
    public void deleteProject(Long id) {
        Projects project = findById(id);
        projectRepository.delete(project);
    }

    @Transactional
    public Projects updateStatus(Long id, StatusSolicitation newStatus) {
        Projects project = findById(id);
        project.setStatus(newStatus);
        return projectRepository.save(project);
    }

    private void validateBusinessRules(Projects project) {
        if (project.getTitle() == null || project.getTitle().isEmpty()) {
            throw new BusinessException(AuthExceptionCodeEnum.INVALID_REQUEST);
        }

        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        if (!userRepository.existsById(project.getUser().getId())) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        if (project.getUser() != null && project.getUser().getId() == null) {
            project.setUser(null);
        }

        if (project.getIdManager() != null && project.getIdManager().getId() == null) {
            project.setIdManager(null);
        }

        if (project.getCoauthors() != null) {
            for (Coauthor coauthor : project.getCoauthors()) {
                coauthor.setProject(project);
            }
        }
    }

    private void validateImageCreateRules(Projects project, MultipartFile file, HttpServletRequest request) {
        try {
            validateBusinessRules(project);

            if (file != null && !file.isEmpty()) {
                String imageUrl = imageService.saveImage(file, request);
                project.setUrlPhoto(imageUrl);
            }

        } catch (IOException e) {
            throw new BusinessException(ProjectExceptionCodeEnum.IMAGE_CREATION_FAILED);
        }
    }

    private void validateUpdateRules(String oldProject, Projects updatedProject) {
        validateBusinessRules(updatedProject);

        if (!oldProject.equals(updatedProject.getTitle()) &&
                projectRepository.existsByTitleAndIdNot(
                        updatedProject.getTitle(),
                        updatedProject.getId()
                )) {
            throw new BusinessException(ProjectExceptionCodeEnum.PROJECT_NOT_FOUND);
        }
    }
}