package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.AcademicProjectResponseDTO;
import br.com.apihubinovacao.domain.models.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAcademicProjectsUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    public List<AcademicProjectResponseDTO> execute() {

        List<AcademicProject> projects = academicProjectRepository.findAll();

        return projects.stream().map(project -> new AcademicProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getUrlPhoto(),
                project.getPdfLink(),
                project.getSiteLink(),
                project.getTypeAP(),
                project.getAuthorEmail(),
                project.getCreationDate().toString()
        )).collect(Collectors.toList());
    }
}