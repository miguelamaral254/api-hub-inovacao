package br.com.apihubinovacao.domain.usecases.user.validate;

import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.*;
import br.com.apihubinovacao.domain.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ValidateUserCredentialsUseCase {

    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PartnerCompanyRepository partnerCompanyRepository;
    private final PasswordEncoder passwordEncoder;

    public ValidateUserCredentialsUseCase(
            AdminRepository adminRepository,
            ManagerRepository managerRepository,
            StudentRepository studentRepository,
            ProfessorRepository professorRepository,
            PartnerCompanyRepository partnerCompanyRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.partnerCompanyRepository = partnerCompanyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserBase execute(String email, String password) {
        UserBase user = Stream.of(
                        adminRepository.findByEmail(email),
                        managerRepository.findByEmail(email),
                        studentRepository.findByEmail(email),
                        professorRepository.findByEmail(email),
                        partnerCompanyRepository.findByEmail(email)
                )
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.LOGIN_FAILED));

        // 🔍 Verificar se a senha está sendo recuperada corretamente
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("User found: " + user.getEmail());
        System.out.println("Stored password (hashed): " + (user.getPassword() != null ? user.getPassword() : "NULL"));
        System.out.println("Provided password: " + password);

        // ⚠️ Se a senha estiver nula ou vazia, lançar erro
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ENCRYPTION_FAILED);
        }

        // 🔑 Validar se a senha encriptada bate com a senha fornecida
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password matches? " + passwordMatches);

        if (!user.isUserStatus()) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }

        if (!passwordMatches) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }

        return user;
    }
}