package br.com.apihubinovacao.domain.usecases.user.get;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.models.*;
import br.com.apihubinovacao.domain.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUsersUseCase {

    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PartnerCompanyRepository partnerCompanyRepository;

    public GetAllUsersUseCase(
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

    public List<UserResponseDTO> execute(Role role) {
        List<? extends User> users;

        switch (role) {
            case ADMIN -> users = adminRepository.findAll();
            case MANAGER -> users = managerRepository.findAll();
            case STUDENT -> users = studentRepository.findAll();
            case PROFESSOR -> users = professorRepository.findAll();
            case PARTNER_COMPANY -> users = partnerCompanyRepository.findAll();
            default -> throw new IllegalArgumentException("Invalid role");
        }

        return users.stream()
                .filter(User::isUserStatus)
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getRole(),
                user.getInstitutionOrganization(),
                user.isUserStatus(),
                user instanceof Admin ? ((Admin) user).getCnpj() :
                        user instanceof PartnerCompany ? ((PartnerCompany) user).getCnpj() : null,
                user instanceof Manager ? ((Manager) user).getCpf() :
                        user instanceof Student ? ((Student) user).getCpf() :
                                user instanceof Professor ? ((Professor) user).getCpf() : null,
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                        .collect(Collectors.toList())
        );
    }
}