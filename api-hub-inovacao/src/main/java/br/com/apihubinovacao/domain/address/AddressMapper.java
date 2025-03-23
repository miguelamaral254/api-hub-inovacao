package br.com.apihubinovacao.domain.address;

import br.com.apihubinovacao.core.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    AddressDTO toDto(Address entity);

    @Override
    Address toEntity(AddressDTO dto);
}