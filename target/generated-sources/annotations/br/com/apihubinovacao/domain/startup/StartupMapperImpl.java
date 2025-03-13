package br.com.apihubinovacao.domain.startup;

import br.com.apihubinovacao.domain.users.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-12T21:35:51-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class StartupMapperImpl implements StartupMapper {

    @Override
    public void mergeNonNull(StartupDTO dto, Startup entity) {
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
        if ( dto.cnpj() != null ) {
            entity.setCnpj( dto.cnpj() );
        }
        if ( dto.feedback() != null ) {
            entity.setFeedback( dto.feedback() );
        }
        if ( dto.justification() != null ) {
            entity.setJustification( dto.justification() );
        }
    }

    @Override
    public StartupDTO toDto(Startup entity) {
        if ( entity == null ) {
            return null;
        }

        Long userId = null;
        Long managerId = null;
        Long id = null;
        String title = null;
        String description = null;
        Boolean enabled = null;
        String cnpj = null;
        String feedback = null;
        String justification = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;

        userId = entityUserId( entity );
        managerId = entityUserMenagerId( entity );
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        enabled = entity.getEnabled();
        cnpj = entity.getCnpj();
        feedback = entity.getFeedback();
        justification = entity.getJustification();
        createdDate = entity.getCreatedDate();
        lastModifiedDate = entity.getLastModifiedDate();

        StartupDTO startupDTO = new StartupDTO( id, title, description, enabled, userId, cnpj, managerId, feedback, justification, createdDate, lastModifiedDate );

        return startupDTO;
    }

    @Override
    public Startup toEntity(StartupDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Startup startup = new Startup();

        startup.setUser( startupDTOToUser( dto ) );
        startup.setUserMenager( startupDTOToUser1( dto ) );
        startup.setId( dto.id() );
        if ( dto.enabled() != null ) {
            startup.setEnabled( dto.enabled() );
        }
        startup.setCreatedDate( dto.createdDate() );
        startup.setLastModifiedDate( dto.lastModifiedDate() );
        startup.setTitle( dto.title() );
        startup.setDescription( dto.description() );
        startup.setCnpj( dto.cnpj() );
        startup.setFeedback( dto.feedback() );
        startup.setJustification( dto.justification() );

        return startup;
    }

    private Long entityUserId(Startup startup) {
        if ( startup == null ) {
            return null;
        }
        User user = startup.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityUserMenagerId(Startup startup) {
        if ( startup == null ) {
            return null;
        }
        User userMenager = startup.getUserMenager();
        if ( userMenager == null ) {
            return null;
        }
        Long id = userMenager.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User startupDTOToUser(StartupDTO startupDTO) {
        if ( startupDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( startupDTO.userId() );

        return user;
    }

    protected User startupDTOToUser1(StartupDTO startupDTO) {
        if ( startupDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( startupDTO.managerId() );

        return user;
    }
}
