package br.com.apihubinovacao.domain.startup;

import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Mapper(componentModel = "spring")
public interface StartupMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "userMenager.id", target = "managerId")
    StartupDTO toDto(Startup entity);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "managerId", target = "userMenager.id")
    Startup toEntity(StartupDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeNonNull(StartupDTO dto, @MappingTarget Startup entity, Consumer<StartupDTO> additionalLogic);

    default Long map(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}