package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.core.BusinessException;
import br.com.apihubinovacao.domain.enterprise.Enterprise;
import br.com.apihubinovacao.domain.enterprise.EnterpriseService;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EnterpriseService enterpriseService;
    private final AuthService jwtService;
    private final AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody AuthRequest authRequest) {
        try {
            User user = userService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
            String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

            AuthDTO response = authMapper.toAuthDTO(user);
            response = new AuthDTO(response.idUser(), token, response.email(), response.role(), "Login successful");
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            try {
                Enterprise enterprise = enterpriseService.authenticateEnterprise(authRequest.getEmail(), authRequest.getPassword());
                String token = jwtService.generateToken(enterprise.getEmail(), "ENTERPRISE");

                AuthDTO response = authMapper.toAuthDTO(enterprise);
                response = new AuthDTO(response.idUser(), token, response.email(), "ENTERPRISE", "Login successful");
                return ResponseEntity.ok(response);
            } catch (BusinessException ex) {
                throw new BusinessException(UserExceptionCodeEnum.INVALID_CREDENTIALS);
            }
        }
    }
}