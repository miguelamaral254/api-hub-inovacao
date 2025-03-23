package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper extends BaseMapper {

    @Mapping(source = "id", target = "idUser")
    AuthDTO toAuthDTO(User user);
}