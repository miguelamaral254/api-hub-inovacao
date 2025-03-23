package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.domain.users.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T11:40:21-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class EditalMapperImpl implements EditalMapper {

    @Override
    public void mergeNonNull(EditalDTO dto, Edital entity) {
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
        if ( dto.title() != null ) {
            entity.setTitle( dto.title() );
        }
        if ( dto.description() != null ) {
            entity.setDescription( dto.description() );
        }
        if ( dto.acessLink() != null ) {
            entity.setAcessLink( dto.acessLink() );
        }
        if ( dto.initialDate() != null ) {
            entity.setInitialDate( dto.initialDate() );
        }
        if ( dto.finalDate() != null ) {
            entity.setFinalDate( dto.finalDate() );
        }
        if ( dto.idUser() != null ) {
            entity.setIdUser( map( dto.idUser() ) );
        }
    }

    @Override
    public EditalDTO toDto(Edital entity) {
        if ( entity == null ) {
            return null;
        }

        Long idUser = null;
        Long id = null;
        String title = null;
        String description = null;
        String acessLink = null;
        LocalDate initialDate = null;
        LocalDate finalDate = null;
        Boolean enabled = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;

        idUser = entityIdUserId( entity );
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        acessLink = entity.getAcessLink();
        initialDate = entity.getInitialDate();
        finalDate = entity.getFinalDate();
        enabled = entity.getEnabled();
        createdDate = entity.getCreatedDate();
        lastModifiedDate = entity.getLastModifiedDate();

        EditalDTO editalDTO = new EditalDTO( id, title, description, acessLink, initialDate, finalDate, idUser, enabled, createdDate, lastModifiedDate );

        return editalDTO;
    }

    @Override
    public Edital toEntity(EditalDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Edital edital = new Edital();

        edital.setIdUser( editalDTOToUser( dto ) );
        edital.setId( dto.id() );
        edital.setEnabled( dto.enabled() );
        edital.setCreatedDate( dto.createdDate() );
        edital.setLastModifiedDate( dto.lastModifiedDate() );
        edital.setTitle( dto.title() );
        edital.setDescription( dto.description() );
        edital.setAcessLink( dto.acessLink() );
        edital.setInitialDate( dto.initialDate() );
        edital.setFinalDate( dto.finalDate() );

        return edital;
    }

    private Long entityIdUserId(Edital edital) {
        if ( edital == null ) {
            return null;
        }
        User idUser = edital.getIdUser();
        if ( idUser == null ) {
            return null;
        }
        Long id = idUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User editalDTOToUser(EditalDTO editalDTO) {
        if ( editalDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( editalDTO.idUser() );

        return user;
    }
}
