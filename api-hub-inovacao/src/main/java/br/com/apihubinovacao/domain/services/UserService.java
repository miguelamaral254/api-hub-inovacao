package br.com.apihubinovacao.domain.services;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserCreateDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRegistration(dto.registration());
        user.setRole(dto.role());
        user.setInstitutionOrganization(dto.institutionOrganization());
        user.setUserStatus(dto.userStatus());

        User savedUser = userRepository.save(user);

        List<PhoneResponseDTO> phones = dto.phones().stream()
                .map(phoneDto -> phoneService.createPhone(phoneDto, savedUser))
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
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
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
}
