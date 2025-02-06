package br.com.apihubinovacao.domain.usecases.startup.update;

import br.com.apihubinovacao.domain.dtos.startups.UpdateStartupStatusDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.projects.Startup;
import br.com.apihubinovacao.domain.repositories.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateStartupStatusUseCase {
    @Autowired
    private StartupRepository startupRepository;

    public void execute(Long startupId, UpdateStartupStatusDTO updateStatusDTO) {
        Optional<Startup> startup = startupRepository.findById(startupId);

        if (startup.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.STARTUP_NOT_FOUND);
        }

        Startup startupData = startup.get();

        startupData.setStatus(updateStatusDTO.status());
        startupData.setFeedback(updateStatusDTO.feedback());
        startupData.setJustification(updateStatusDTO.justification());
        startupData.setIdManager(updateStatusDTO.idManager());

        startupRepository.save(startupData);
    }
}
