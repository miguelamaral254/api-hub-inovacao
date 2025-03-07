package br.com.apihubinovacao.domain.users;


import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;

@Tag(name = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


        @Tag(name="Create User")
        @PostMapping
        @Operation(summary = "Create a new user")
        public ResponseEntity<Void> createUser(
                @Validated(CreateValidation.class)
                @RequestBody UserDTO userDto) {
            User user = userMapper.toEntity(userDto);
            User savedEntity = userService.createUser(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedEntity.getId())
                    .toUri();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(location)
                    .build();
        }

    @Tag(name="Search Users with filter")
    @GetMapping
    @Operation(summary = "Search users with filters or all users")
    public ResponseEntity<ApplicationResponse<Page<UserDTO>>> searchUsers(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            Pageable pageable) {

        Specification<User> specification = Specification.where(null);

        if (role != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("role"), role));
        }
        if (cpf != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("cpf"), cpf));
        }
        if (email != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("email"), "%" + email + "%"));
        }

        Page<User> userPage = userService.searchUsers(specification, pageable);
        Page<UserDTO> userDTOPage = userMapper.toDto(userPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(userDTOPage));
    }



}

