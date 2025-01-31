package br.com.apihubinovacao.domain.usecases;

import br.com.apihubinovacao.domain.dtos.PublishResponseDTO;
import br.com.apihubinovacao.domain.models.Publish;
import br.com.apihubinovacao.domain.repositories.PublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllPublishUseCase {
    @Autowired
    private PublishRepository publishRepository;

    public List<PublishResponseDTO> execute() {
        LocalDate today = LocalDate.now();

        return publishRepository.findAll().stream()
                .filter(publish -> !today.isAfter(publish.getFinalDate().plusDays(7)))
                .map(publish -> new PublishResponseDTO(
                        publish.getId(),
                        publish.getTitle(),
                        publish.getDescription(),
                        publish.getAcessLink(),
                        publish.getPhotoLink(),
                        publish.getInitialDate(),
                        publish.getFinalDate(),
                        publish.getPublishedDate()
                ))
                .collect(Collectors.toList());
    }
}
