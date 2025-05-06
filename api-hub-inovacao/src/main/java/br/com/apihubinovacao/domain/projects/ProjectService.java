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
import java.util.Collections;
import java.util.function.Consumer;

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
    @Transactional
    public Projects updateProject(Long id, Consumer<Projects> updateConsumer) {
        Projects existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ProjectExceptionCodeEnum.PROJECT_NOT_FOUND));

        updateConsumer.accept(existingProject);

        validateUpdateBusiness(id, existingProject);

        return projectRepository.save(existingProject);
    }

    private void validateUpdateBusiness(Long id, Projects existingProject) {
        if (existingProject.getTitle() == null || existingProject.getTitle().isEmpty()) {
            throw new BusinessException(ProjectExceptionCodeEnum.INVALID_PROJECT_TITLE);
        }

        if (existingProject.getUrlPhoto() != null) {
            Projects existingUrlProject = projectRepository.findByUrlPhotoAndIdNot(existingProject.getUrlPhoto(), id);

            if (existingUrlProject != null) {
                throw new BusinessException(ProjectExceptionCodeEnum.PROJECT_URL_ALREADY_EXISTS);
            }
        }

        if (existingProject.getStatus() == null) {
            throw new BusinessException(ProjectExceptionCodeEnum.INVALID_PROJECT_STATUS);
        }

        if (existingProject.getCoauthors() == null) {
            existingProject.setCoauthors(Collections.emptyList());
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


}