package br.com.apihubinovacao.domain.usecases.publish;

import br.com.apihubinovacao.domain.dtos.publish.PublishResponseDTO;
import br.com.apihubinovacao.domain.models.Publish;
import br.com.apihubinovacao.domain.repositories.PublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllPublishUseCase {
    @Autowired
    private PublishRepository publishRepository;

    public Page<PublishResponseDTO> execute(int page, int size) {
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page, size);

        Page<Publish> publishPage = publishRepository.findAll(pageable);

        List<PublishResponseDTO> filteredPublishList = publishPage.getContent().stream()
                .filter(publish -> !today.isAfter(publish.getFinalDate().plusDays(7))) // Filtrando com base na data
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

        return new PageImpl<>(filteredPublishList, pageable, publishPage.getTotalElements());
    }
}