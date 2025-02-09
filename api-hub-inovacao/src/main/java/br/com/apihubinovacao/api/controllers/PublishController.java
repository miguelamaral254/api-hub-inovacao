package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.publish.PublishCreateDTO;
import br.com.apihubinovacao.domain.dtos.publish.PublishResponseDTO;
import br.com.apihubinovacao.domain.dtos.publish.UpdatePublishDetailsDTO;
import br.com.apihubinovacao.domain.usecases.publish.CreatePublishForManagerUseCase;
import br.com.apihubinovacao.domain.usecases.publish.ListAllPublishUseCase;
import br.com.apihubinovacao.domain.usecases.publish.UpdatePublishDetailsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publish")
public class PublishController {
    private final CreatePublishForManagerUseCase createPublishForManagerUseCase;
    private final ListAllPublishUseCase listAllPublishUseCase;
    private final UpdatePublishDetailsUseCase updatePublishDetailsUseCase;

    public PublishController(
            CreatePublishForManagerUseCase createPublishForManagerUseCase,
            ListAllPublishUseCase listAllPublishUseCase,
            UpdatePublishDetailsUseCase updatePublishDetailsUseCase
    ) {
        this.createPublishForManagerUseCase = createPublishForManagerUseCase;
        this.listAllPublishUseCase = listAllPublishUseCase;
        this.updatePublishDetailsUseCase = updatePublishDetailsUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<PublishResponseDTO> createPublish(@RequestBody PublishCreateDTO publishCreateDTO) {
        PublishResponseDTO createdPublish = createPublishForManagerUseCase.execute(publishCreateDTO);
        return ResponseEntity.ok(createdPublish);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PublishResponseDTO>> getAllPublish() {
        List<PublishResponseDTO> publishs = listAllPublishUseCase.execute();
        return ResponseEntity.ok(publishs);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{publishId}/update")
    public ResponseEntity<Void> updatePublish (
            @PathVariable Long publishId,
            @RequestBody UpdatePublishDetailsDTO dto) {
        updatePublishDetailsUseCase.execute(publishId, dto);
        return ResponseEntity.noContent().build();
    }
}
