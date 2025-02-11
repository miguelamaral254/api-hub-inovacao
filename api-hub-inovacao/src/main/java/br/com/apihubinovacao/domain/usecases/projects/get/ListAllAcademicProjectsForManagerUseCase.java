package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListAllAcademicProjectsForManagerUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<?> execute() {
        List<AcademicProject> allProjects = academicProjectRepository.findAll();

        return allProjects.stream()
                .map(project -> {
                    Optional<Professor> professor = professorRepository.findByEmail(project.getAuthorEmail());
                    Optional<Student> student = studentRepository.findByEmail(project.getAuthorEmail());

                    if (professor.isPresent()) {
                        return new AcademicProjectResponseProfessorDTO(
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
                                professor.get().getId(),
                                professor.get().getName(),
                                project.getFeedback(),
                                project.getJustification(),
                                project.getIdManager(),
                                project.getCoauthors() != null && !project.getCoauthors().isEmpty() ? project.getCoauthors().stream()
                                        .map(coauthor -> new CoauthorDTO(
                                                coauthor.getName(),
                                                coauthor.getEmail(),
                                                coauthor.getPhone()
                                        ))
                                        .collect(Collectors.toList()) : new ArrayList<>()
                        );
                    }
                    else if (student.isPresent()) {
                        return new AcademicProjectResponseStudentDTO(
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
                                student.get().getId(),
                                student.get().getName(),
                                project.getFeedback(),
                                project.getJustification(),
                                project.getIdManager(),
                                project.getCoauthors() != null && !project.getCoauthors().isEmpty() ? project.getCoauthors().stream()
                                        .map(coauthor -> new CoauthorDTO(
                                                coauthor.getName(),
                                                coauthor.getEmail(),
                                                coauthor.getPhone()
                                        ))
                                        .collect(Collectors.toList()) : new ArrayList<>()
                        );
                    } else {
                        throw new BusinessException(ErrorCodeEnum.AUTHOR_NOT_FOUND);
                    }
                })
                .collect(Collectors.toList());
    }
}