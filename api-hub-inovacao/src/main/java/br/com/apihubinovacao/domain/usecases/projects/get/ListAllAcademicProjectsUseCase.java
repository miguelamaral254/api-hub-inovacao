package br.com.apihubinovacao.domain.usecases.projects.get;

import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseProfessorDTO;
import br.com.apihubinovacao.domain.dtos.projects.AcademicProjectResponseStudentDTO;
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
        // Busca todos os projetos no repositório
        List<AcademicProject> projects = academicProjectRepository.findAll();

        // Para cada projeto, verifica se o autor é um professor ou estudante
        return projects.stream()
                .map(project -> {
                    Optional<Professor> professor = professorRepository.findByEmail(project.getAuthorEmail());
                    Optional<Student> student = studentRepository.findByEmail(project.getAuthorEmail());

                    if (professor.isPresent()) {
                        // Se o autor for um professor, retorna o DTO de professor
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
                                professor.get().getName()
                        );
                    } else if (student.isPresent()) {
                        // Se o autor for um estudante, retorna o DTO de estudante
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
                                student.get().getName()
                        );
                    } else {
                        throw new RuntimeException("Autor do projeto não encontrado: " + project.getAuthorEmail());
                    }
                })
                .collect(Collectors.toList());
    }
}