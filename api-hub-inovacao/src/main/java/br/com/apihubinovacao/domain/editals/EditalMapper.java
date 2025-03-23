package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EditalMapper extends BaseMapper<Edital, EditalDTO> {

    @Mapping(source = "idUser.id", target = "idUser")
    EditalDTO toDto(Edital entity);

    @Mapping(source = "idUser", target = "idUser.id")
    Edital toEntity(EditalDTO dto);

    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Long map(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}