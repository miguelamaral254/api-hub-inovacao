package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public Projects createProject(Projects project) {
        validateBusinessRules(project);
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
                .orElseThrow(()-> new BusinessException(ErrorCodeEnum.PROJECT_NOT_FOUND));
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
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }

        if (!userRepository.existsById(project.getUser().getId())) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }
    }

    private void validateUpdateRules(String oldProject, Projects updatedProject) {
        validateBusinessRules(updatedProject);

        if (!oldProject.equals(updatedProject.getTitle()) &&
                projectRepository.existsByTitleAndIdNot(
                        updatedProject.getTitle(),
                        updatedProject.getId()
                )) {
            throw new BusinessException(ErrorCodeEnum.PROJECT_NOT_FOUND);
        }
    }
}
