package br.com.apihubinovacao.domain.services;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserCreateDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.Role;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserCreateDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        try {
            user.setPassword(passwordEncoder.encode(dto.password()));
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ENCRYPTION_FAILED);
        }
        user.setRegistration(dto.registration());
        user.setRole(dto.role());
        user.setInstitutionOrganization(dto.institutionOrganization());
        user.setUserStatus(dto.userStatus());

        User savedUser = userRepository.save(user);

        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> {
                    try {
                        return phoneService.createPhone(phoneDto, savedUser);
                    } catch (Exception e) {
                        throw new BusinessException(ErrorCodeEnum.PHONE_CREATION_FAILED);
                    }
                })
                .collect(Collectors.toList());

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRegistration(),
                savedUser.getRole(),
                savedUser.getInstitutionOrganization(),
                savedUser.isUserStatus(),
                phones
        );
    }

    public User validateUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.LOGIN_FAILED));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_FAILED);
        }

        return user;
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USER_NOT_FOUND));
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getRole(),
                user.getInstitutionOrganization(),
                user.isUserStatus(),
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                        .collect(Collectors.toList())
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findByRole(Role.STUDENT).stream()
                .filter(User::isUserStatus)
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRegistration(),
                        user.getRole(),
                        user.getInstitutionOrganization(),
                        user.isUserStatus(),
                        user.getPhones().stream()
                                .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public Page<UserResponseDTO> getAllPlatformUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable).map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistration(),
                user.getRole(),
                user.getInstitutionOrganization(),
                user.isUserStatus(),
                user.getPhones().stream()
                        .map(phone -> new PhoneResponseDTO(phone.getId(), phone.getNumber()))
                        .collect(Collectors.toList())
        ));
    }
}
