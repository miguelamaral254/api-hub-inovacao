package br.com.apihubinovacao.api.controllers;

import br.com.apihubinovacao.domain.dtos.PublishCreateDTO;
import br.com.apihubinovacao.domain.dtos.PublishResponseDTO;
import br.com.apihubinovacao.domain.usecases.CreatePublishForManagerUseCase;
import br.com.apihubinovacao.domain.usecases.ListAllPublishUseCase;
import br.com.apihubinovacao.domain.usecases.user.get.GetAllUsersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publish")
public class PublishController {
    private final CreatePublishForManagerUseCase createPublishForManagerUseCase;
    private final ListAllPublishUseCase listAllPublishUseCase;

    public PublishController(
            CreatePublishForManagerUseCase createPublishForManagerUseCase,
            ListAllPublishUseCase listAllPublishUseCase
    ) {
        this.createPublishForManagerUseCase = createPublishForManagerUseCase;
        this.listAllPublishUseCase = listAllPublishUseCase;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("create-publish")
    public ResponseEntity<PublishResponseDTO> createPublishForManager(@RequestBody PublishCreateDTO publishCreateDTO) {
        PublishResponseDTO createdPublish = createPublishForManagerUseCase.execute(publishCreateDTO);
        return ResponseEntity.ok(createdPublish);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PublishResponseDTO>> getAllPublishForManager() {
        List<PublishResponseDTO> publishs = listAllPublishUseCase.execute();
        return ResponseEntity.ok(publishs);
    }
}
