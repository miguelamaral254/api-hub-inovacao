package br.com.apihubinovacao.domain.usecases.startup.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseProfessorApprovedDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentApprovedDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
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
public class ListAllStartupsUseCase {
    @Autowired
    private StartupRepository startupRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<?> execute() {
        List<Startup> approvedStartups = startupRepository.findAll().stream()
                .filter(startup -> StatusSolicitation.APROVADA.name().equalsIgnoreCase(startup.getStatus().name()))
                .collect(Collectors.toList());

        return approvedStartups.stream()
                .map(startup -> {
                    Optional<Professor> professor = professorRepository.findByEmail(startup.getAuthorEmail());
                    Optional<Student> student = studentRepository.findByEmail(startup.getAuthorEmail());

                    if (professor.isPresent()) {
                        return new StartupResponseProfessorApprovedDTO(
                                startup.getId(),
                                startup.getTitle(),
                                startup.getDescription(),
                                startup.getUrlPhoto(),
                                startup.getPdfLink(),
                                startup.getSiteLink(),
                                startup.getCnpj(),
                                startup.getAuthorEmail(),
                                startup.getCreationDate().toString(),
                                professor.get().getId(),
                                professor.get().getName(),
                                startup.getCoauthors() != null && !startup.getCoauthors().isEmpty() ? startup.getCoauthors().stream()
                                        .map(coauthor -> new CoauthorDTO(coauthor.getName(), coauthor.getEmail(), coauthor.getPhone()))
                                        .collect(Collectors.toList()) : new ArrayList<>()
                        );
                    }
                    // Se for estudante
                    else if (student.isPresent()) {
                        return new StartupResponseStudentApprovedDTO(
                                startup.getId(),
                                startup.getTitle(),
                                startup.getDescription(),
                                startup.getUrlPhoto(),
                                startup.getPdfLink(),
                                startup.getSiteLink(),
                                startup.getCnpj(),
                                startup.getAuthorEmail(),
                                startup.getCreationDate().toString(),
                                student.get().getId(),
                                student.get().getName(),
                                startup.getCoauthors() != null && !startup.getCoauthors().isEmpty() ? startup.getCoauthors().stream()
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
