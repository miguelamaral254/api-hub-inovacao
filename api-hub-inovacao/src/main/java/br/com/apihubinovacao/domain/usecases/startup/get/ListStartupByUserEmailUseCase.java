package br.com.apihubinovacao.domain.usecases.startup.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseProfessorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListStartupByUserEmailUseCase {
    @Autowired
    private StartupRepository startupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public List<?> execute(String email) {
        Optional<Professor> professor = professorRepository.findByEmail(email);
        Optional<Student> student = studentRepository.findByEmail(email);

        if (professor.isEmpty() && student.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
        }

        List<Startup> startups = startupRepository.findAll()
                .stream()
                .filter(project -> email.equals(project.getAuthorEmail()))
                .collect(Collectors.toList());

        if (professor.isPresent()) {
            return startups.stream().map(startup -> new StartupResponseProfessorDTO(
                    startup.getId(),
                    startup.getTitle(),
                    startup.getDescription(),
                    startup.getUrlPhoto(),
                    startup.getPdfLink(),
                    startup.getSiteLink(),
                    startup.getAuthorEmail(),
                    startup.getCreationDate().toString(),
                    startup.getStatus(),
                    professor.get().getId(),
                    professor.get().getName(),
                    startup.getFeedback(),       // Novo campo
                    startup.getJustification(),  // Novo campo
                    startup.getIdManager(),
                    startup.getCnpj(),
                    startup.getCoauthors() != null ? startup.getCoauthors().stream()
                            .map(coauthor -> new CoauthorDTO(
                                    coauthor.getName(),
                                    coauthor.getEmail(),
                                    coauthor.getPhone()
                            ))
                            .collect(Collectors.toList()) : new ArrayList<>()
            )).collect(Collectors.toList());
        } else {
            return startups.stream().map(startup -> new StartupResponseStudentDTO(
                    startup.getId(),
                    startup.getTitle(),
                    startup.getDescription(),
                    startup.getUrlPhoto(),
                    startup.getPdfLink(),
                    startup.getSiteLink(),
                    startup.getAuthorEmail(),
                    startup.getCreationDate().toString(),
                    startup.getStatus(),
                    student.get().getId(),
                    student.get().getName(),
                    startup.getFeedback(),
                    startup.getJustification(),
                    startup.getIdManager(),
                    startup.getCnpj(),
                    startup.getCoauthors() != null ? startup.getCoauthors().stream()
                            .map(coauthor -> new CoauthorDTO(
                                    coauthor.getName(),
                                    coauthor.getEmail(),
                                    coauthor.getPhone()
                            ))
                            .collect(Collectors.toList()) : new ArrayList<>()
            )).collect(Collectors.toList());
        }
    }
}
