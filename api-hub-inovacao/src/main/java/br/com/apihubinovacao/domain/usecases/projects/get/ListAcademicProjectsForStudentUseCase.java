package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentDTO;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAcademicProjectsForStudentUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public List<AcademicProjectResponseStudentDTO> execute() {

        List<AcademicProject> projects = academicProjectRepository.findAll()
                .stream()
                .filter(project -> project.getStudent() != null)
                .collect(Collectors.toList());

        return projects.stream().map(project -> new AcademicProjectResponseStudentDTO(
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
                project.getStudent().getId(),
                project.getStudent().getName(),
                project.getFeedback(),
                project.getJustification(),
                project.getIdManager(),
                project.getCoauthors() != null ? project.getCoauthors().stream()
                        .map(coauthor -> new CoauthorDTO(
                                coauthor.getName(),
                                coauthor.getEmail(),
                                coauthor.getPhone()
                        ))
                        .collect(Collectors.toList()) : null
        )).collect(Collectors.toList());
    }
}