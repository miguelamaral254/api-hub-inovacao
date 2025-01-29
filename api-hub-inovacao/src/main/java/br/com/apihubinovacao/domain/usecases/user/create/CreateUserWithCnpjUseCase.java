package br.com.apihubinovacao.domain.usecases.user.create;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserCreateCnpjDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.Admin;
import br.com.apihubinovacao.domain.models.PartnerCompany;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.AdminRepository;
import br.com.apihubinovacao.domain.repositories.PartnerCompanyRepository;
import br.com.apihubinovacao.domain.services.PhoneService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateUserWithCnpjUseCase {

    private final AdminRepository adminRepository;
    private final PartnerCompanyRepository partnerCompanyRepository;
    private final PhoneService phoneService;
    private final PasswordEncoder passwordEncoder;

    public CreateUserWithCnpjUseCase(
            AdminRepository adminRepository,
            PartnerCompanyRepository partnerCompanyRepository,
            PhoneService phoneService,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.partnerCompanyRepository = partnerCompanyRepository;
        this.phoneService = phoneService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO execute(UserCreateCnpjDTO dto) {
        validateUserInput(dto.email(), dto.password(), dto.cnpj());

        User user = createUserInstance(dto);
        setCommonFields(user, dto);
        User savedUser = saveUser(user);

        return saveAndReturn(savedUser, dto);
    }

    private void validateUserInput(String email, String password, String cnpj) {
        if (email == null || email.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_EMAIL);
        if (password == null || password.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
        if (cnpj == null || cnpj.isEmpty()) throw new BusinessException(ErrorCodeEnum.INVALID_CNPJ);

        if (adminRepository.findByEmail(email).isPresent() ||
                partnerCompanyRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }
    }

    private User createUserInstance(UserCreateCnpjDTO dto) {
        if (dto.role() == Role.ADMIN) {
            Admin admin = new Admin();
            admin.setCnpj(dto.cnpj());
            return admin;
        } else if (dto.role() == Role.PARTNER_COMPANY) {
            PartnerCompany partnerCompany = new PartnerCompany();
            partnerCompany.setCnpj(dto.cnpj());
            return partnerCompany;
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }
    }

    private void setCommonFields(User user, UserCreateCnpjDTO dto) {
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password())); // 🔒 Encriptação da senha
        user.setRegistration(dto.registration());
        user.setRole(dto.role());
        user.setInstitutionOrganization(dto.institutionOrganization());
        user.setUserStatus(dto.userStatus());
    }

    private User saveUser(User user) {
        return user instanceof Admin ? adminRepository.save((Admin) user) : partnerCompanyRepository.save((PartnerCompany) user);
    }

    private UserResponseDTO saveAndReturn(User savedUser, UserCreateCnpjDTO dto) {
        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> phoneService.createPhone(phoneDto, savedUser))
                .collect(Collectors.toList());

        return new UserResponseDTO(
                savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRegistration(), savedUser.getRole(),
                savedUser.getInstitutionOrganization(), savedUser.isUserStatus(),
                savedUser instanceof Admin ? ((Admin) savedUser).getCnpj() : null,
                null, phones
        );
    }
}