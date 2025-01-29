package br.com.apihubinovacao.domain.usecases.user.create;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserCreateCpfDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.Manager;
import br.com.apihubinovacao.domain.models.Professor;
import br.com.apihubinovacao.domain.models.Student;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.ManagerRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import br.com.apihubinovacao.domain.services.PhoneService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateUserWithCpfUseCase {

    private final ManagerRepository managerRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PhoneService phoneService;
    private final PasswordEncoder passwordEncoder;

    public CreateUserWithCpfUseCase(
            ManagerRepository managerRepository,
            StudentRepository studentRepository,
            ProfessorRepository professorRepository,
            PhoneService phoneService,
            PasswordEncoder passwordEncoder
    ) {
        this.managerRepository = managerRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.phoneService = phoneService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO execute(UserCreateCpfDTO dto) {
        validateUserInput(dto.email(), dto.password(), dto.cpf());

        User user = createUserInstance(dto);
        setCommonFields(user, dto);
        User savedUser = saveUser(user);

        return saveAndReturn(savedUser, dto);
    }

    private void validateUserInput(String email, String password, String cpf) {
        if (email == null || email.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_EMAIL);
        if (password == null || password.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
        if (cpf == null || cpf.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_CPF);

        if (managerRepository.findByEmail(email).isPresent() ||
                studentRepository.findByEmail(email).isPresent() ||
                professorRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }
    }

    private User createUserInstance(UserCreateCpfDTO dto) {
        if (dto.role() == Role.MANAGER) {
            Manager manager = new Manager();
            manager.setCpf(dto.cpf());
            return manager;
        } else if (dto.role() == Role.STUDENT) {
            Student student = new Student();
            student.setCpf(dto.cpf());
            return student;
        } else if (dto.role() == Role.PROFESSOR) {
            Professor professor = new Professor();
            professor.setCpf(dto.cpf());
            return professor;
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }
    }

    private void setCommonFields(User user, UserCreateCpfDTO dto) {
        user.setName(dto.name());
        user.setEmail(dto.email());

        String encodedPassword = passwordEncoder.encode(dto.password());
        System.out.println("Senha original: " + dto.password());
        System.out.println("Senha criptografada: " + encodedPassword);

        user.setPassword(encodedPassword);
        user.setRegistration(dto.registration());
        user.setRole(dto.role());
        user.setInstitutionOrganization(dto.institutionOrganization());
        user.setUserStatus(dto.userStatus());
    }
    private User saveUser(User user) {
        if (user instanceof Manager) return managerRepository.save((Manager) user);
        else if (user instanceof Student) return studentRepository.save((Student) user);
        else return professorRepository.save((Professor) user);
    }

    private UserResponseDTO saveAndReturn(User savedUser, UserCreateCpfDTO dto) {
        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> phoneService.createPhone(phoneDto, savedUser))
                .collect(Collectors.toList());

        return new UserResponseDTO(
                savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRegistration(), savedUser.getRole(),
                savedUser.getInstitutionOrganization(), savedUser.isUserStatus(),
                null, savedUser instanceof Manager ? ((Manager) savedUser).getCpf() :
                savedUser instanceof Student ? ((Student) savedUser).getCpf() :
                        ((Professor) savedUser).getCpf(), phones
        );
    }
}