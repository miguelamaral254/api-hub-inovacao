package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.PublishCreateDTO;
import br.com.apihubinovacao.domain.dtos.PublishResponseDTO;
import br.com.apihubinovacao.domain.usecases.CreatePublishForManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {
    private final CreatePublishForManager createPublishForManager;

    public PublishController(CreatePublishForManager createPublishForManager) {
        this.createPublishForManager = createPublishForManager;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGERS')")
    @PostMapping("create-publish")
    public ResponseEntity<PublishResponseDTO> createPublishForManager(@RequestBody PublishCreateDTO publishCreateDTO) {
        PublishResponseDTO createdPublish =createPublishForManager.execute(publishCreateDTO);
        return ResponseEntity.ok(createdPublish);
    }

}
