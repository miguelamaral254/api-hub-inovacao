package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional()
    public Projects createProject(Projects project) {
        validateBusinessRules(project);
        return projectRepository.save(project);

    }




    private void validateBusinessRules(Projects project) {
        // 1. Validação do título
        if (project.getTitle() == null || project.getTitle().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        // 2. Validação do usuário
        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }

        // Verificar se o usuário existe no banco
        if (!userRepository.existsById(project.getUser().getId())) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }
    }
}
