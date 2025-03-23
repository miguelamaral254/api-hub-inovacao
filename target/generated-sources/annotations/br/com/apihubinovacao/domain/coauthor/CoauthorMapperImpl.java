package br.com.apihubinovacao.domain.coauthor;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T11:40:22-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class CoauthorMapperImpl implements CoauthorMapper {

    @Override
    public void mergeNonNull(CoauthorDTO dto, Coauthor entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.id() != null ) {
            entity.setId( dto.id() );
        }
        if ( dto.enabled() != null ) {
            entity.setEnabled( dto.enabled() );
        }
        if ( dto.createdDate() != null ) {
            entity.setCreatedDate( dto.createdDate() );
        }
        if ( dto.lastModifiedDate() != null ) {
            entity.setLastModifiedDate( dto.lastModifiedDate() );
        }
        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
        if ( dto.email() != null ) {
            entity.setEmail( dto.email() );
        }
        if ( dto.phone() != null ) {
            entity.setPhone( dto.phone() );
        }
    }

    @Override
    public CoauthorDTO toDto(Coauthor entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String phone = null;
        Boolean enabled = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;

        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        enabled = entity.getEnabled();
        createdDate = entity.getCreatedDate();
        lastModifiedDate = entity.getLastModifiedDate();

        CoauthorDTO coauthorDTO = new CoauthorDTO( id, name, email, phone, enabled, createdDate, lastModifiedDate );

        return coauthorDTO;
    }

    @Override
    public Coauthor toEntity(CoauthorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Coauthor coauthor = new Coauthor();

        coauthor.setId( dto.id() );
        coauthor.setEnabled( dto.enabled() );
        coauthor.setCreatedDate( dto.createdDate() );
        coauthor.setLastModifiedDate( dto.lastModifiedDate() );
        coauthor.setName( dto.name() );
        coauthor.setEmail( dto.email() );
        coauthor.setPhone( dto.phone() );

        return coauthor;
    }
}
