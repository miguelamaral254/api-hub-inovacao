package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<Projects, ProjectsDTO> {

    @Mapping(source = "user.id", target = "idUser")
    @Mapping(source = "idManager.id", target = "idManager")
    ProjectsDTO toDto(Projects entity);

    @Mapping(source = "idUser", target = "user.id")
    @Mapping(source = "idManager", target = "idManager.id")
    Projects toEntity(ProjectsDTO dto);

    // Método auxiliar para mapear User para Long (para idUser e idManager)
    default Long map(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    // Método auxiliar para mapear Long para User (para idManager no método toEntity)
    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}