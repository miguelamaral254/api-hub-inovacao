package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        validateBusinessRules(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsers(
            Specification<User> specification,
            Pageable pageable
    ) {
        return userRepository.findAll(specification, pageable);
    }

    @Transactional()
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.EMAIL_DOES_NOT_MATCH));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
        }
        return user;
    }

    private void validateBusinessRules(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_EMAIL);
        }

        if (user.getCpf() != null && userRepository.existsByCpf(user.getCpf())) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_CPF);
        }

        if (user.getCnpj() != null && userRepository.existsByCnpj(user.getCnpj())) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_CNPJ);
        }

        if (userRepository.existsByRegistration(user.getRegistration())) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_REGISTRATION);
        }

        if (user.getEmail() == null || !user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            throw new BusinessException(ErrorCodeEnum.INVALID_EMAIL);
        }

    }

}