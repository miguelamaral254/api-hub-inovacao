package br.com.apihubinovacao.domain.usecases.startup.get;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.StartupResponseProfessorDTO;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListStartupsForProfessorUseCase {
    @Autowired
    private StartupRepository startupRepository;

    public List<StartupResponseProfessorDTO> execute() {
        List<Startup> startups = startupRepository.findAll()
                .stream()
                .filter(startup -> startup.getProfessor() != null)
                .collect(Collectors.toList());

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
                startup.getProfessor().getId(),
                startup.getProfessor().getName(),
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
