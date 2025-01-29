package br.com.apihubinovacao.domain.usecases.user.get;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseCnpjDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseCpfDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.*;
import br.com.apihubinovacao.domain.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GetUserByEmailUseCase {

    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PartnerCompanyRepository partnerCompanyRepository;

    public GetUserByEmailUseCase(
            AdminRepository adminRepository,
            ManagerRepository managerRepository,
            StudentRepository studentRepository,
            ProfessorRepository professorRepository,
            PartnerCompanyRepository partnerCompanyRepository
    ) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.partnerCompanyRepository = partnerCompanyRepository;
    }

    public Object execute(String email) {
        User user = Stream.of(
                        adminRepository.findByEmail(email),
                        managerRepository.findByEmail(email),
                        studentRepository.findByEmail(email),
                        professorRepository.findByEmail(email),
                        partnerCompanyRepository.findByEmail(email)
                )
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USER_NOT_FOUND));

        return user instanceof Admin || user instanceof PartnerCompany
                ? convertToUserResponseCnpjDTO(user)
                : convertToUserResponseCpfDTO(user);
    }

    private UserResponseCnpjDTO convertToUserResponseCnpjDTO(User user) {
        return new UserResponseCnpjDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getRole(),
                user.getInstitutionOrganization(),
                user.isUserStatus(),
                user instanceof Admin ? ((Admin) user).getCnpj() :
                        user instanceof PartnerCompany ? ((PartnerCompany) user).getCnpj() : null,
                // O campo CPF não é utilizado para Admin e PartnerCompany
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                        .collect(Collectors.toList())
        );
    }

    private UserResponseCpfDTO convertToUserResponseCpfDTO(User user) {
        return new UserResponseCpfDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getRole(),
                user.getInstitutionOrganization(),
                user.isUserStatus(),
                user instanceof Manager ? ((Manager) user).getCpf() :
                        user instanceof Student ? ((Student) user).getCpf() :
                                user instanceof Professor ? ((Professor) user).getCpf() : null,
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                        .collect(Collectors.toList())
        );
    }
}