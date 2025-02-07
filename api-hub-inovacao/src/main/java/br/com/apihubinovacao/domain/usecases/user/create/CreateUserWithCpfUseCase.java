package br.com.apihubinovacao.domain.usecases.user.create;

import br.com.apihubinovacao.domain.dtos.phone.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.user.UserCreateCpfDTO;
import br.com.apihubinovacao.domain.dtos.user.UserResponseCpfDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.users.Manager; // Adicionando o import do Manager
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import br.com.apihubinovacao.domain.models.users.User;
import br.com.apihubinovacao.domain.repositories.ManagerRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import br.com.apihubinovacao.domain.usecases.phone.create.CreatePhoneUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateUserWithCpfUseCase {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CreatePhoneUseCase createPhoneUseCase;
    private final PasswordEncoder passwordEncoder;
    private final ManagerRepository managerRepository;

    public CreateUserWithCpfUseCase(
            StudentRepository studentRepository,
            ProfessorRepository professorRepository,
            CreatePhoneUseCase createPhoneUseCase,
            PasswordEncoder passwordEncoder,
            ManagerRepository managerRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.createPhoneUseCase = createPhoneUseCase;
        this.passwordEncoder = passwordEncoder;
        this.managerRepository = managerRepository;
    }

    public UserResponseCpfDTO execute(UserCreateCpfDTO dto) {
        validateUserInput(dto.email(), dto.password(), dto.cpf(), dto.registration(), dto.role());

        User user = createUserInstance(dto);
        setCommonFields(user, dto);
        User savedUser = saveUser(user);

        return saveAndReturn(savedUser, dto);
    }

    private void validateUserInput(String email, String password, String cpf, String registration, Role role) {
        if (email == null || email.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_EMAIL);
        if (password == null || password.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
        if (cpf == null || cpf.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_CPF);
        if (registration == null || registration.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_REGISTRATION);

        if (studentRepository.findByEmail(email).isPresent() || professorRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }

        if (studentRepository.findByCpf(cpf).isPresent() || professorRepository.findByCpf(cpf).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_CPF);
        }

        if (studentRepository.findByRegistration(registration).isPresent() || professorRepository.findByRegistration(registration).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_REGISTRATION);
        }

        // Verificar se a role é válida
        if (role == null || !Role.MANAGER.equals(role) && !Role.STUDENT.equals(role) && !Role.PROFESSOR.equals(role)) {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }
    }

    private User createUserInstance(UserCreateCpfDTO dto) {
        if (dto.role() == Role.STUDENT) {
            Student student = new Student();
            student.setCpf(dto.cpf());
            return student;
        } else if (dto.role() == Role.PROFESSOR) {
            Professor professor = new Professor();
            professor.setCpf(dto.cpf());
            return professor;
        } else if (dto.role() == Role.MANAGER) {
            // Criação de um manager
            Manager manager = new Manager();
            manager.setCpf(dto.cpf());
            return manager;
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }
    }

    private void setCommonFields(User user, UserCreateCpfDTO dto) {
        user.setName(dto.name());
        user.setEmail(dto.email());
        String encodedPassword = passwordEncoder.encode(dto.password());
        user.setPassword(encodedPassword);
        user.setRegistration(dto.registration());
        user.setRole(dto.role());
        user.setInstitutionOrganization(dto.institutionOrganization());

        // Aqui, garantimos que o status do usuário seja sempre true ao ser criado
        user.setUserStatus(true);
    }

    private User saveUser(User user) {
        if (user instanceof Student) return studentRepository.save((Student) user);
        else if (user instanceof Professor) return professorRepository.save((Professor) user);
        else if (user instanceof Manager) {
            // Salvar Manager
            return managerRepository.save((Manager) user);
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }
    }

    private UserResponseCpfDTO saveAndReturn(User savedUser, UserCreateCpfDTO dto) {
        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> createPhoneUseCase.execute(phoneDto, savedUser))
                .collect(Collectors.toList());

        return new UserResponseCpfDTO(
                savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRegistration(), savedUser.getRole(),
                savedUser.getInstitutionOrganization(), savedUser.isUserStatus(),
                savedUser instanceof Student ? ((Student) savedUser).getCpf() :
                        savedUser instanceof Professor ? ((Professor) savedUser).getCpf() : ((Manager) savedUser).getCpf(),
                phones
        );
    }
}