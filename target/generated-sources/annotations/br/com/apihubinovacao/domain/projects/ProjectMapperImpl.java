package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.coauthor.Coauthor;
import br.com.apihubinovacao.domain.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.coauthor.CoauthorMapper;
import br.com.apihubinovacao.domain.users.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T11:40:21-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    private CoauthorMapper coauthorMapper;

    @Override
    public void mergeNonNull(ProjectsDTO dto, Projects entity) {
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
        if ( dto.urlPhoto() != null ) {
            entity.setUrlPhoto( dto.urlPhoto() );
        }
        if ( dto.pdfLink() != null ) {
            entity.setPdfLink( dto.pdfLink() );
        }
        if ( dto.siteLink() != null ) {
            entity.setSiteLink( dto.siteLink() );
        }
        if ( dto.projectType() != null ) {
            entity.setProjectType( dto.projectType() );
        }
        if ( dto.status() != null ) {
            entity.setStatus( dto.status() );
        }
        if ( dto.idManager() != null ) {
            entity.setIdManager( map( dto.idManager() ) );
        }
        if ( dto.feedback() != null ) {
            entity.setFeedback( dto.feedback() );
        }
        if ( dto.justification() != null ) {
            entity.setJustification( dto.justification() );
        }
        if ( entity.getCoauthors() != null ) {
            List<Coauthor> list = coauthorMapper.toEntity( dto.coauthors() );
            if ( list != null ) {
                entity.getCoauthors().clear();
                entity.getCoauthors().addAll( list );
            }
        }
        else {
            List<Coauthor> list = coauthorMapper.toEntity( dto.coauthors() );
            if ( list != null ) {
                entity.setCoauthors( list );
            }
        }
    }

    @Override
    public ProjectsDTO toDto(Projects entity) {
        if ( entity == null ) {
            return null;
        }

        Long idUser = null;
        Long idManager = null;
        List<CoauthorDTO> coauthors = null;
        Long id = null;
        String title = null;
        String description = null;
        String urlPhoto = null;
        String pdfLink = null;
        String siteLink = null;
        ProjectType projectType = null;
        StatusSolicitation status = null;
        String feedback = null;
        String justification = null;
        Boolean enabled = null;
        LocalDateTime createdDate = null;
        LocalDateTime lastModifiedDate = null;

        idUser = entityUserId( entity );
        idManager = entityIdManagerId( entity );
        coauthors = coauthorMapper.toDto( entity.getCoauthors() );
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        urlPhoto = entity.getUrlPhoto();
        pdfLink = entity.getPdfLink();
        siteLink = entity.getSiteLink();
        projectType = entity.getProjectType();
        status = entity.getStatus();
        feedback = entity.getFeedback();
        justification = entity.getJustification();
        enabled = entity.getEnabled();
        createdDate = entity.getCreatedDate();
        lastModifiedDate = entity.getLastModifiedDate();

        ProjectsDTO projectsDTO = new ProjectsDTO( id, title, description, urlPhoto, pdfLink, siteLink, projectType, status, idUser, idManager, feedback, justification, enabled, createdDate, lastModifiedDate, coauthors );

        return projectsDTO;
    }

    @Override
    public Projects toEntity(ProjectsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Projects projects = new Projects();

        projects.setUser( projectsDTOToUser( dto ) );
        projects.setIdManager( projectsDTOToUser1( dto ) );
        projects.setCoauthors( coauthorMapper.toEntity( dto.coauthors() ) );
        projects.setId( dto.id() );
        projects.setEnabled( dto.enabled() );
        projects.setCreatedDate( dto.createdDate() );
        projects.setLastModifiedDate( dto.lastModifiedDate() );
        projects.setTitle( dto.title() );
        projects.setDescription( dto.description() );
        projects.setUrlPhoto( dto.urlPhoto() );
        projects.setPdfLink( dto.pdfLink() );
        projects.setSiteLink( dto.siteLink() );
        projects.setProjectType( dto.projectType() );
        projects.setStatus( dto.status() );
        projects.setFeedback( dto.feedback() );
        projects.setJustification( dto.justification() );

        return projects;
    }

    private Long entityUserId(Projects projects) {
        if ( projects == null ) {
            return null;
        }
        User user = projects.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityIdManagerId(Projects projects) {
        if ( projects == null ) {
            return null;
        }
        User idManager = projects.getIdManager();
        if ( idManager == null ) {
            return null;
        }
        Long id = idManager.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User projectsDTOToUser(ProjectsDTO projectsDTO) {
        if ( projectsDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( projectsDTO.idUser() );

        return user;
    }

    protected User projectsDTOToUser1(ProjectsDTO projectsDTO) {
        if ( projectsDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( projectsDTO.idManager() );

        return user;
    }
}
