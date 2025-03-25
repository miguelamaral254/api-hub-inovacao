package br.com.apihubinovacao.domain.opportunity;

import br.com.apihubinovacao.core.BaseMapper;
import br.com.apihubinovacao.domain.enterprise.EnterpriseMapper;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.domain.users.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EnterpriseMapper.class})
public interface OpportunityMapper extends BaseMapper<Opportunity, OpportunityDTO> {

    @Mapping(source = "enterprise.id", target = "enterpriseId")
    @Mapping(source = "idManager.id", target = "managerId")
    @Mapping(source = "status", target = "status")
    OpportunityDTO toDto(Opportunity entity);

    @Mapping(source = "enterpriseId", target = "enterprise.id")
    @Mapping(source = "managerId", target = "idManager.id")
    @Mapping(source = "status", target = "status")
    Opportunity toEntity(OpportunityDTO dto);

    default Long mapUserToId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    default User mapIdToUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

}