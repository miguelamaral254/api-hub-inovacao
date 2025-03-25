package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.core.BusinessException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(String email, String role) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role", "ROLE_" + role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        } catch (Exception e) {
            throw new BusinessException(AuthExceptionCodeEnum.SERVER_ERROR);
        }
    }

    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            return role != null && (
                    role.equals("ROLE_ADMIN") ||
                            role.equals("ROLE_PROFESSOR") ||
                            role.equals("ROLE_STUDENT") ||
                            role.equals("ROLE_MANAGER") ||
                            role.equals("ROLE_ENTERPRISE")
            );
        } catch (Exception e) {
            throw new BusinessException(AuthExceptionCodeEnum.INVALID_TOKEN);
        }
    }

    // Método para extrair o e-mail do token
    public String extractEmail(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new BusinessException(AuthExceptionCodeEnum.INVALID_TOKEN);
        }
    }
}