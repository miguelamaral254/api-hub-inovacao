package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.domain.enterprise.Enterprise;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "id", target = "idUser")
    AuthDTO toAuthDTO(User user);

    @Mapping(source = "id", target = "idUser")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    AuthDTO toAuthDTO(Enterprise enterprise);
}