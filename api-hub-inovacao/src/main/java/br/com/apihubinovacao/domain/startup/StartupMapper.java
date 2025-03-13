package br.com.apihubinovacao.domain.startup;


import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

@Mapper(componentModel = "spring")
public interface StartupMapper extends BaseMapper<Startup, StartupDTO> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "userMenager.id", target = "managerId")
    StartupDTO toDto(Startup entity);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "managerId", target = "userMenager.id")

    Startup toEntity(StartupDTO dto);

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
