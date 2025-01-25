package br.com.apihubinovacao.domain.services;

import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.dtos.UserCreateDTO;
import br.com.apihubinovacao.domain.dtos.UserResponseDTO;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneService phoneService;

    public UserResponseDTO createUser(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
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
}
