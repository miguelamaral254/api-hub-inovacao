package br.com.apihubinovacao.domain.usecases.user.create;

import br.com.apihubinovacao.domain.dtos.phone.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.user.UserCreateCpfDTO;
import br.com.apihubinovacao.domain.dtos.user.UserResponseCpfDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.users.Manager;
import br.com.apihubinovacao.domain.repositories.ManagerRepository;
import br.com.apihubinovacao.domain.usecases.phone.create.CreatePhoneUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateManagerUserUseCase {

    private final ManagerRepository managerRepository;
    private final CreatePhoneUseCase createPhoneUseCase;
    private final PasswordEncoder passwordEncoder;

    public CreateManagerUserUseCase(
            ManagerRepository managerRepository,
            CreatePhoneUseCase createPhoneUseCase,
            PasswordEncoder passwordEncoder
    ) {
        this.managerRepository = managerRepository;
        this.createPhoneUseCase = createPhoneUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseCpfDTO execute(UserCreateCpfDTO dto) {
        validateManagerInput(dto.email(), dto.password(), dto.cpf(), dto.registration());

        Manager manager = createManagerInstance(dto);
        setManagerFields(manager, dto);
        Manager savedManager = saveManager(manager);

        return saveAndReturn(savedManager, dto);
    }

    private void validateManagerInput(String email, String password, String cpf, String registration) {
        if (email == null || email.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_EMAIL);
        if (password == null || password.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
        if (cpf == null || cpf.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_CPF);
        if (registration == null || registration.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_REGISTRATION);

        if (managerRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }

        if (managerRepository.findByCpf(cpf).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_CPF);
        }

        if (managerRepository.findByRegistration(registration).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_REGISTRATION);
        }
    }

    private Manager createManagerInstance(UserCreateCpfDTO dto) {
        Manager manager = new Manager();
        manager.setCpf(dto.cpf());
        return manager;
    }

    private void setManagerFields(Manager manager, UserCreateCpfDTO dto) {
        manager.setName(dto.name());
        manager.setEmail(dto.email());
        String encodedPassword = passwordEncoder.encode(dto.password());
        manager.setPassword(encodedPassword);
        manager.setRegistration(dto.registration());
        manager.setRole(dto.role());
        manager.setInstitutionOrganization(dto.institutionOrganization());
        manager.setUserStatus(dto.userStatus());
    }

    private Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    private UserResponseCpfDTO saveAndReturn(Manager savedManager, UserCreateCpfDTO dto) {
        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> createPhoneUseCase.execute(phoneDto, savedManager))
                .collect(Collectors.toList());

        return new UserResponseCpfDTO(
                savedManager.getId(), savedManager.getName(), savedManager.getEmail(),
                savedManager.getRegistration(), savedManager.getRole(),
                savedManager.getInstitutionOrganization(), savedManager.isUserStatus(),
                savedManager.getCpf(), phones
        );
    }
}