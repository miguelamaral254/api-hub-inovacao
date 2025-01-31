package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorApprovedDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentApprovedDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListAllAcademicProjectsUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<?> execute() {
        // Filtra os projetos com status "APROVADA"
        List<AcademicProject> approvedProjects = academicProjectRepository.findAll().stream()
                .filter(project -> StatusSolicitation.APROVADA.name().equalsIgnoreCase(project.getStatus().name()))  // Comparação do enum com o nome
                .collect(Collectors.toList());

        return approvedProjects.stream()
                .map(project -> {
                    Optional<Professor> professor = professorRepository.findByEmail(project.getAuthorEmail());
                    Optional<Student> student = studentRepository.findByEmail(project.getAuthorEmail());

                    // Se for professor
                    if (professor.isPresent()) {
                        return new AcademicProjectResponseProfessorApprovedDTO(
                                project.getId(),
                                project.getTitle(),
                                project.getDescription(),
                                project.getUrlPhoto(),
                                project.getPdfLink(),
                                project.getSiteLink(),
                                project.getTypeAP(),
                                project.getAuthorEmail(),
                                project.getCreationDate().toString(),
                                project.getIdManager(),
                                professor.get().getId(),
                                project.getCoauthors() != null && !project.getCoauthors().isEmpty() ? project.getCoauthors().stream()
                                        .map(coauthor -> new CoauthorDTO(
                                                coauthor.getName(),
                                                coauthor.getEmail(),
                                                coauthor.getPhone()
                                        ))
                                        .collect(Collectors.toList()) : null // Verifica se existe coautor
                        );
                    }
                    // Se for estudante
                    else if (student.isPresent()) {
                        return new AcademicProjectResponseStudentApprovedDTO(
                                project.getId(),
                                project.getTitle(),
                                project.getDescription(),
                                project.getUrlPhoto(),
                                project.getPdfLink(),
                                project.getSiteLink(),
                                project.getTypeAP(),
                                project.getAuthorEmail(),
                                project.getCreationDate().toString(),
                                student.get().getId(),
                                student.get().getName(),
                                project.getCoauthors() != null && !project.getCoauthors().isEmpty() ? project.getCoauthors().stream()
                                        .map(coauthor -> new CoauthorDTO(
                                                coauthor.getName(),
                                                coauthor.getEmail(),
                                                coauthor.getPhone()
                                        ))
                                        .collect(Collectors.toList()) : null // Verifica se existe coautor
                        );
                    } else {
                        throw new RuntimeException("Autor do projeto não encontrado: " + project.getAuthorEmail());
                    }
                })
                .collect(Collectors.toList());
    }
}