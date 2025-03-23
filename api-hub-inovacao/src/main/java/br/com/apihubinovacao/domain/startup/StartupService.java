package br.com.apihubinovacao.domain.startup;


import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserRepository;
import br.com.apihubinovacao.core.BusinessException;
import br.com.apihubinovacao.domain.users.UserExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class StartupService {
    private final StartupRepository startupRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Startup createStartup(Startup startup) {
        validate(startup);
        return startupRepository.save(startup);
    }

    private void validate(Startup startup) {
        User user = userService.findById(startup.getUser().getId());
        User manager = null;
        if (startup.getUserMenager() != null && startup.getUserMenager().getId() != null) {
            manager = userService.findById(startup.getUserMenager().getId());
        }

        startup.setUser(user);
        startup.setUserMenager(manager);

        validadeBusinessRules(startup);
        validateUniqueFields(startup);

    }

    private void validadeBusinessRules(Startup startup) {
        if (startup.getUser() == null || startup.getUser().getId() == null) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        userService.findById(startup.getUser().getId());
    }


    private void validateUniqueFields(Startup startup) {
        if (startup.getTitle() == null || startupRepository.existsByTitle(startup.getTitle())) {
            throw new BusinessException(StartupExceptionCodeEnum.INVALID_STARTUP_TITLE);
        }
        if (startup.getCnpj() == null || startupRepository.existsByCnpj(startup.getCnpj())) {
            throw new BusinessException(UserExceptionCodeEnum.DUPLICATE_CNPJ);
        }
    }
    @Transactional(readOnly = true)
    public Page<Startup> searchStartup(Specification<Startup> specification, Pageable pageable) {
        return startupRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Startup findById(Long id) {
        return startupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(StartupExceptionCodeEnum.STARTUP_NOT_FOUND));
    }
    @Transactional
    public Startup updateStartup(Long id, Consumer<Startup> mergeNonNull) {
        Startup existingStartup = startupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(StartupExceptionCodeEnum.STARTUP_NOT_FOUND));

        // Armazena os valores antigos para validação posterior
        final String oldTitle = existingStartup.getTitle();
        final String oldDescription = existingStartup.getDescription();
        final Boolean oldEnabled = existingStartup.getEnabled();
        final StatusSolicitation oldStatus = existingStartup.getStatus();  // Alterado para StatusSolicitation
        final Long oldUserId = existingStartup.getUser() != null ? existingStartup.getUser().getId() : null;

        // Aplica a lógica de atualização usando o Consumer
        mergeNonNull.accept(existingStartup);

        // Valida as alterações feitas
        validateUpdate(existingStartup, oldTitle, oldDescription, oldEnabled, oldStatus, oldUserId);

        // Salva a entidade com as mudanças
        return startupRepository.save(existingStartup);
    }

    private void validateUpdate(Startup updatedStartup, String oldTitle, String oldDescription,
                                Boolean oldEnabled, StatusSolicitation oldStatus, Long oldUserId) {
        // Verifica se o título foi alterado e, se sim, garante que o novo título não seja vazio
        if (!updatedStartup.getTitle().equals(oldTitle) && (updatedStartup.getTitle() == null || updatedStartup.getTitle().isEmpty())) {
            throw new BusinessException(StartupExceptionCodeEnum.INVALID_STARTUP_TITLE);
        }

        // Verifica se a descrição foi alterada e, se sim, garante que a nova descrição não seja vazia
        if (!updatedStartup.getDescription().equals(oldDescription) && (updatedStartup.getDescription() == null || updatedStartup.getDescription().isEmpty())) {
            throw new BusinessException(StartupExceptionCodeEnum.INVALID_STARTUP_DESCRIPTION);
        }

        // Verifica se o status foi alterado para um valor inválido
        if (!updatedStartup.getStatus().equals(oldStatus) && !isValidStatus(updatedStartup.getStatus())) {
            throw new BusinessException(StartupExceptionCodeEnum.INVALID_STARTUP_STATUS);
        }

        if (!updatedStartup.getUser().getId().equals(oldUserId) && updatedStartup.getUser() == null) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        if (updatedStartup.getEnabled() != null && !updatedStartup.getEnabled().equals(oldEnabled)) {
        }
    }

    private boolean isValidStatus(StatusSolicitation status) {
        // Defina suas regras de validação para o status aqui
        return StatusSolicitation.APROVADA.equals(status) || StatusSolicitation.REPROVADA.equals(status) || StatusSolicitation.PENDENTE.equals(status);
    }

    @Transactional
    public void deleteStartup(Long id) {
        if (!startupRepository.existsById(id)) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }
        startupRepository.deleteById(id);
    }
}