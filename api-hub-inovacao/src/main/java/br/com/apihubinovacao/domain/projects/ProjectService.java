package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.errors.exceptions.BusinessException;
import br.com.apihubinovacao.domain.errors.exceptions.GeneralExceptionCodeEnum;
import br.com.apihubinovacao.domain.errors.exceptions.ProjectExceptionCodeEnum;
import br.com.apihubinovacao.domain.errors.exceptions.UserExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.UserRepository;
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
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public Projects createProject(Projects project, MultipartFile file, HttpServletRequest request) throws IOException {
        validateBusinessRules(project);

        if (project.getUser() != null && project.getUser().getId() == null) {
            project.setUser(null);
        }

        if (project.getIdManager() != null && project.getIdManager().getId() == null) {
            project.setIdManager(null);
        }
        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.saveImage(file, request);
            project.setUrlPhoto(imageUrl);
        }

        if (project.getCoauthors() != null) {
            for (Coauthor coauthor : project.getCoauthors()) {
                coauthor.setProject(project);
            }
        }
        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Page<Projects> searchProjects(
            Specification<Projects> specification,
            Pageable pageable
    ) {
        return projectRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Projects findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ProjectExceptionCodeEnum.PROJECT_NOT_FOUND));
    }

    @Transactional()
    public Projects updateProject(Long id, Consumer<Projects> cc) {
        Projects project = findById(id);
        final String oldTitle = project.getTitle();
        cc.accept(project);
        validateUpdateRules(oldTitle, project);
        return project;
    }



    private void validateBusinessRules(Projects project) {
        if (project.getTitle() == null || project.getTitle().isEmpty()) {
            throw new BusinessException(GeneralExceptionCodeEnum.INVALID_REQUEST);
        }

        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        if (!userRepository.existsById(project.getUser().getId())) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
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
