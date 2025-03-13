package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.domain.users.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T11:40:21-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public Object toDto(Object entity) {
        if ( entity == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object toEntity(Object dto) {
        if ( dto == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public void mergeNonNull(Object dto, Object entity) {
        if ( dto == null ) {
            return;
        }
    }

    @Override
    public AuthDTO toAuthDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long idUser = null;
        String email = null;
        String role = null;

        idUser = user.getId();
        email = user.getEmail();
        if ( user.getRole() != null ) {
            role = user.getRole().name();
        }

        String token = null;
        String message = null;

        AuthDTO authDTO = new AuthDTO( idUser, token, email, role, message );

        return authDTO;
    }
}
