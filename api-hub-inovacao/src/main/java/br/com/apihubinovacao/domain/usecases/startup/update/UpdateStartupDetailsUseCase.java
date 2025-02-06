package br.com.apihubinovacao.domain.usecases.startup.update;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.dtos.startups.UpdateStartupDetailsDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.Coauthor;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UpdateStartupDetailsUseCase {

    @Autowired
    private StartupRepository startupRepository;

    public void execute(Long startupId, UpdateStartupDetailsDTO updateDTO) {
        Optional<Startup> startupOpt = startupRepository.findById(startupId);

        if (startupOpt.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.STARTUP_NOT_FOUND);
        }

        Startup startup = startupOpt.get();

        if (updateDTO.title() != null) {
            startup.setTitle(updateDTO.title());
        }
        if (updateDTO.description() != null) {
            startup.setDescription(updateDTO.description());
        }
        if (updateDTO.urlPhoto() != null) {
            startup.setUrlPhoto(updateDTO.urlPhoto());
        }
        if (updateDTO.pdfLink() != null) {
            startup.setPdfLink(updateDTO.pdfLink());
        }
        if (updateDTO.siteLink() != null) {
            startup.setSiteLink(updateDTO.siteLink());
        }
        if (updateDTO.cnpj() != null) {
            startup.setCnpj(updateDTO.cnpj());
        }

        if (updateDTO.coauthors() != null) {
            updateCoauthors(startup, updateDTO.coauthors());
        }


        startupRepository.save(startup);
    }

    private void updateCoauthors(Startup startup, List<CoauthorDTO> coauthorDTOs) {
        List<Coauthor> existingCoauthors = startup.getCoauthors();

        for (CoauthorDTO dto : coauthorDTOs) {
            Optional<Coauthor> existingCoauthorOpt = existingCoauthors.stream()
                    .filter(coauthor -> coauthor.getEmail().equals(dto.email()))
                    .findFirst();

            if (existingCoauthorOpt.isPresent()) {
                Coauthor existingCoauthor = existingCoauthorOpt.get();

                if (dto.name() != null) {
                    existingCoauthor.setName(dto.name());
                }
                if (dto.phone() != null) {
                    existingCoauthor.setPhone(dto.phone());
                }

            } else {
                Coauthor newCoauthor = new Coauthor(
                        dto.name() != null ? dto.name() : "Nome Padrão",
                        dto.email(),
                        dto.phone() != null ? dto.phone() : "Telefone Padrão",
                        startup,
                        null
                );
                existingCoauthors.add(newCoauthor);
            }
        }

        startup.setCoauthors(existingCoauthors);

    }

}
