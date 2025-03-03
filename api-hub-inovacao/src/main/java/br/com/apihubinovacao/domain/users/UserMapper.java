package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.core.BaseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    @Mapping(target = "password", source = "password")
    User toEntity(UserDTO userDto);

    @Override
    @Mapping(target = "password", source = "password")
    UserDTO toDto(User user);

    @AfterMapping
    default void afterToEntity(UserDTO userDto, @MappingTarget User user) {
        if (user.getPhones() != null) {
            user.getPhones().forEach(phone -> phone.setUser(user));
        }
    }
}