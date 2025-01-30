package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAcademicProjectsForProfessorUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public List<AcademicProjectResponseProfessorDTO> execute() {

        List<AcademicProject> projects = academicProjectRepository.findAll()
                .stream()
                .filter(project -> project.getProfessor() != null)
                .collect(Collectors.toList());

        return projects.stream().map(project -> new AcademicProjectResponseProfessorDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getUrlPhoto(),
                project.getPdfLink(),
                project.getSiteLink(),
                project.getTypeAP(),
                project.getAuthorEmail(),
                project.getCreationDate().toString(),
                project.getStatus(),
                project.getProfessor().getId(),
                project.getProfessor().getName(),
                project.getFeedback(),
                project.getJustification(),
                project.getIdManager(),
                project.getCoauthors() != null ? project.getCoauthors().stream()
                        .map(coauthor -> new CoauthorDTO(
                                coauthor.getName(),
                                coauthor.getEmail(),
                                coauthor.getPhone()
                        ))
                        .collect(Collectors.toList()) : null// Novo campo
        )).collect(Collectors.toList());
    }
}