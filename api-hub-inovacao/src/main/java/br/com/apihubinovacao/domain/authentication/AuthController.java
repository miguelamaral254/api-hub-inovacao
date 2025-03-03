package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService jwtService;


    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody AuthRequest authRequest) {
        User user = userService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        AuthDTO response = new AuthDTO(user.getEmail(), token, user.getRole().name(), "Login successful");

        return ResponseEntity.ok(response);
    }
}