package br.com.apihubinovacao.domain.usecases.user.get;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseCnpjDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseCpfDTO;
import br.com.apihubinovacao.domain.models.users.*;
import br.com.apihubinovacao.domain.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GetAllPlatformUsersUseCase {

    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PartnerCompanyRepository partnerCompanyRepository;

    public GetAllPlatformUsersUseCase(
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

    public Page<Object> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<User> users = Stream.of(
                        adminRepository.findAll(),
                        managerRepository.findAll(),
                        studentRepository.findAll(),
                        professorRepository.findAll(),
                        partnerCompanyRepository.findAll()
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());

        List<Object> pagedUsers = users.subList(start, end).stream()
                .map(this::convertToUserResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(pagedUsers, pageable, users.size());
    }

    private Object convertToUserResponseDTO(User user) {
        if (user instanceof Admin || user instanceof PartnerCompany) {
            return new UserResponseCnpjDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRegistration(),
                    user.getRole(),
                    user.getInstitutionOrganization(),
                    user.isUserStatus(),
                    user instanceof Admin ? ((Admin) user).getCnpj() : ((PartnerCompany) user).getCnpj(),
                    user.getPhones().stream()
                            .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                            .collect(Collectors.toList())
            );
        } else {
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
                                    ((Professor) user).getCpf(),
                    user.getPhones().stream()
                            .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                            .collect(Collectors.toList())
            );
        }
    }
}