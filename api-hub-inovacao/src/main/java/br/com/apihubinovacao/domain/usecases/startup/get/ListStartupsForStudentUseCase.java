package br.com.apihubinovacao.domain.usecases.startup.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseStudentDTO;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListStartupsForStudentUseCase {
    @Autowired
    private StartupRepository startupRepository;

    public List<StartupResponseStudentDTO> execute() {
        List<Startup> startups = startupRepository.findAll()
                .stream()
                .filter(startup -> startup.getStudent() != null)
                .collect(Collectors.toList());

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
                startup.getStudent().getId(),
                startup.getStudent().getName(),
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
                        .collect(Collectors.toList()) : null

        )).collect(Collectors.toList());
    }
}
