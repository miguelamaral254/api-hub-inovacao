package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.domain.phone.Phone;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T11:40:21-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void mergeNonNull(UserDTO dto, User entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.id() != null ) {
            entity.setId( dto.id() );
        }
        if ( dto.enabled() != null ) {
            entity.setEnabled( dto.enabled() );
        }
        if ( dto.createdDate() != null ) {
            entity.setCreatedDate( dto.createdDate() );
        }
        if ( dto.lastModifiedDate() != null ) {
            entity.setLastModifiedDate( dto.lastModifiedDate() );
        }
        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
        if ( dto.registration() != null ) {
            entity.setRegistration( dto.registration() );
        }
        if ( dto.role() != null ) {
            entity.setRole( dto.role() );
        }
        if ( entity.getPhones() != null ) {
            List<Phone> list = dto.phones();
            if ( list != null ) {
                entity.getPhones().clear();
                entity.getPhones().addAll( list );
            }
        }
        else {
            List<Phone> list = dto.phones();
            if ( list != null ) {
                entity.setPhones( new ArrayList<Phone>( list ) );
            }
        }
        if ( dto.email() != null ) {
            entity.setEmail( dto.email() );
        }
        if ( dto.password() != null ) {
            entity.setPassword( dto.password() );
        }
        if ( dto.cpf() != null ) {
            entity.setCpf( dto.cpf() );
        }
        if ( dto.cnpj() != null ) {
            entity.setCnpj( dto.cnpj() );
        }

        afterToEntity( dto, entity );
    }

    @Override
    public User toEntity(UserDTO userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( userDto.password() );
        user.setId( userDto.id() );
        user.setEnabled( userDto.enabled() );
        user.setCreatedDate( userDto.createdDate() );
        user.setLastModifiedDate( userDto.lastModifiedDate() );
        user.setName( userDto.name() );
        user.setRegistration( userDto.registration() );
        user.setRole( userDto.role() );
        List<Phone> list = userDto.phones();
        if ( list != null ) {
            user.setPhones( new ArrayList<Phone>( list ) );
        }
        user.setEmail( userDto.email() );
        user.setCpf( userDto.cpf() );
        user.setCnpj( userDto.cnpj() );

        afterToEntity( userDto, user );

        return user;
    }

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String password = null;
        Long id = null;
        String name = null;
        String registration = null;
        Role role = null;
        List<Phone> phones = null;
        String email = null;
        Boolean enabled = null;
        String cpf = null;
        String cnpj = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;

        password = user.getPassword();
        id = user.getId();
        name = user.getName();
        registration = user.getRegistration();
        role = user.getRole();
        List<Phone> list = user.getPhones();
        if ( list != null ) {
            phones = new ArrayList<Phone>( list );
        }
        email = user.getEmail();
        enabled = user.getEnabled();
        cpf = user.getCpf();
        cnpj = user.getCnpj();
        createdDate = user.getCreatedDate();
        lastModifiedDate = user.getLastModifiedDate();

        UserDTO userDTO = new UserDTO( id, name, registration, role, phones, email, password, enabled, cpf, cnpj, createdDate, lastModifiedDate );

        return userDTO;
    }
}
