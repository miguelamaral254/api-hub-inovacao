package br.com.apihubinovacao.domain.usecases.startup.create;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupCreateStudentDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.Coauthor;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import br.com.apihubinovacao.domain.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateStartupForStudent {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StartupRepository startupRepository;

    private void validateCreate(StartupCreateStudentDTO startupCreateStudentDTO) {
        if (startupCreateStudentDTO.title() == null || startupCreateStudentDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (startupCreateStudentDTO.description() == null || startupCreateStudentDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
    }

    public StartupResponseStudentDTO execute(StartupCreateStudentDTO createDTO){
        validateCreate(createDTO);
        Startup startup = new Startup();
        startup.setTitle(createDTO.title());
        startup.setDescription(createDTO.description());
        startup.setUrlPhoto(createDTO.urlPhoto());
        startup.setPdfLink(createDTO.pdfLink());
        startup.setSiteLink(createDTO.siteLink());
        startup.setStatus(createDTO.status());
        startup.setAuthorEmail(createDTO.userEmail());
        startup.setCnpj(createDTO.cnpj());
        startup.setFlagActive(true);
        startup.setCreationDate(LocalDate.now());

        startup.setStudent(studentRepository.findById(createDTO.studentId())
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.USER_NOT_FOUND)));


        if (createDTO.coauthors() != null) {
            List<Coauthor> coauthors = createDTO.coauthors().stream()
                    .map(coauthorDTO -> {
                        Coauthor coauthor = new Coauthor();
                        coauthor.setName(coauthorDTO.name());
                        coauthor.setEmail(coauthorDTO.email());
                        coauthor.setPhone(coauthorDTO.phone());
                        coauthor.setStartup(startup);
                        return coauthor;
                    })
                    .collect(Collectors.toList());
            startup.setCoauthors(coauthors);

        }

        startup.setFeedback(null);
        startup.setJustification(null);
        startup.setIdManager(null);

        Startup savedStartup = startupRepository.save(startup);



        return new StartupResponseStudentDTO(
                savedStartup.getId(),
                savedStartup.getTitle(),
                savedStartup.getDescription(),
                savedStartup.getUrlPhoto(),
                savedStartup.getPdfLink(),
                savedStartup.getSiteLink(),
                savedStartup.getAuthorEmail(),
                savedStartup.getCreationDate().toString(),
                savedStartup.getStatus(),
                savedStartup.getStudent().getId(),
                savedStartup.getStudent().getName(),
                savedStartup.getFeedback(),
                savedStartup.getJustification(),
                savedStartup.getIdManager(),
                savedStartup.getCnpj(),
                savedStartup.getCoauthors() != null ? savedStartup.getCoauthors().stream()
                        .map(coauthor -> new CoauthorDTO(
                                coauthor.getName(),
                                coauthor.getEmail(),
                                coauthor.getPhone()
                        ))
                        .collect(Collectors.toList()) : null


        );
    }
}
