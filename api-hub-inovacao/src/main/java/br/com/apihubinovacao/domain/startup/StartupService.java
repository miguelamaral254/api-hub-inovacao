package br.com.apihubinovacao.domain.startup;


import br.com.apihubinovacao.domain.errors.exceptions.StartupExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserRepository;
import br.com.apihubinovacao.domain.errors.exceptions.BusinessException;
import br.com.apihubinovacao.domain.errors.exceptions.UserExceptionCodeEnum;
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
    public Startup updateStartup(Long id, Startup startup) {
        Startup existingStartup = startupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(StartupExceptionCodeEnum.STARTUP_NOT_FOUND));

        existingStartup.setTitle(startup.getTitle());
        existingStartup.setDescription(startup.getDescription());
        existingStartup.setEnabled(startup.getEnabled());
        existingStartup.setStatus(startup.getStatus());
        existingStartup.setUser(startup.getUser());

        return startupRepository.save(existingStartup);
    }


    @Transactional
    public void deleteStartup(Long id) {
        if (!startupRepository.existsById(id)) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }
        startupRepository.deleteById(id);
    }
}