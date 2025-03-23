package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper extends BaseMapper<Enterprise, EnterpriseDTO> {

    @Override
    @Mapping(source = "address", target = "address")
    EnterpriseDTO toDto(Enterprise entity);

    @Override
    @Mapping(source = "address", target = "address")
    Enterprise toEntity(EnterpriseDTO dto);
}