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

    // Mapeamento adicional para converter o ID do usuário para o objeto User
    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    // Mapeamento adicional para converter o objeto User para o ID
    default Long map(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}