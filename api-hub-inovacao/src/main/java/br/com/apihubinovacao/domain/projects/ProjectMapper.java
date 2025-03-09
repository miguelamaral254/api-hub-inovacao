package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.coauthor.CoauthorMapper;
import br.com.apihubinovacao.domain.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CoauthorMapper.class})
public interface ProjectMapper extends BaseMapper<Projects, ProjectsDTO> {

    @Mapping(source = "user.id", target = "idUser")
    @Mapping(source = "idManager.id", target = "idManager")
    @Mapping(source = "coauthors", target = "coauthors")
    ProjectsDTO toDto(Projects entity);

    @Mapping(source = "idUser", target = "user.id")
    @Nullable
    @Mapping(source = "idManager", target = "idManager.id")
    @Mapping(source = "coauthors", target = "coauthors")
    Projects toEntity(ProjectsDTO dto);

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

    default List<Coauthor> mapCoauthors(List<Coauthor> coauthors) {

        return coauthors;
    }
}