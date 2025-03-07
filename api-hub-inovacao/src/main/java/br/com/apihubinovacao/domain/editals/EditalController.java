package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.ApplicationResponse;
import br.com.apihubinovacao.validations.CreateValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Controller
@RequestMapping("/editals")
@AllArgsConstructor
public class EditalController {

    private final EditalService editalService;
    private final EditalMapper editalMapper;

    @Tag(name = "Create Edital")
    @Operation(summary = "Create a new Edital")
    @PostMapping()
    public ResponseEntity<Void> createEdital(
            @Validated(CreateValidation.class)
            @RequestBody EditalDTO editalDto) {

        Edital edital = editalMapper.toEntity(editalDto);
        Edital savedEntity = editalService.createEdital(edital);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEntity.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @Tag(name="Search Edital with filter")
    @GetMapping
    @Operation(summary = "Search Editals with filters or all Editals")
    public ResponseEntity<ApplicationResponse<Page<EditalDTO>>> searchEditals(Pageable pageable) {

        Specification<Edital> specification = Specification.where(null);


        Page<Edital> editalPage = editalService.searchEditals(specification, pageable);
        Page<EditalDTO> editalDTOPage = editalMapper.toDto(editalPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApplicationResponse.ofSuccess(editalDTOPage));
    }



}
