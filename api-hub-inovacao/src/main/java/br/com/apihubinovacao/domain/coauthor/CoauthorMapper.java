package br.com.apihubinovacao.domain.coauthor;

import br.com.apihubinovacao.core.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoauthorMapper extends BaseMapper<Coauthor, CoauthorDTO> {

    @Override
    CoauthorDTO toDto(Coauthor entity);

    @Override
    Coauthor toEntity(CoauthorDTO dto);
}