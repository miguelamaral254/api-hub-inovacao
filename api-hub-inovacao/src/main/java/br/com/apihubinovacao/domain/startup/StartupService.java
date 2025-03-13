package br.com.apihubinovacao.domain.startup;


import br.com.apihubinovacao.domain.errors.exceptions.StartupExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.UserRepository;
import br.com.apihubinovacao.domain.errors.exceptions.BusinessException;
import br.com.apihubinovacao.domain.errors.exceptions.UserExceptionCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StartupService {
    private final StartupRepository startupRepository;
    private final UserRepository userRepository;

    @Transactional
    public Startup createStartup(Startup startup) {
        startup.setUser(user);
        validateUniqueFields(startup);
        return startupRepository.save(startup);
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
    public void deleteStartup(Long id) {
        if (!startupRepository.existsById(id)) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }
        startupRepository.deleteById(id);
    }
}
