package br.com.apihubinovacao.domain.usecases;

import br.com.apihubinovacao.domain.dtos.publish.UpdatePublishDetailsDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.Publish;
import br.com.apihubinovacao.domain.repositories.PublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePublishDetailsUseCase {

    @Autowired
    private PublishRepository publishRepository;

    public void execute(Long publishId, UpdatePublishDetailsDTO updatePublishDetailsDTO) {
        Optional<Publish> publishOptional = publishRepository.findById(publishId);

        if (publishOptional.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PUBLISH_NOT_FOUND);
        }

        Publish publish = publishOptional.get();

        if (updatePublishDetailsDTO.title()!= null){
            publish.setTitle(updatePublishDetailsDTO.title());
        }
        if (updatePublishDetailsDTO.description()!= null){
            publish.setDescription(updatePublishDetailsDTO.description());
        }
        if (updatePublishDetailsDTO.acessLink()!= null){
            publish.setAcessLink(updatePublishDetailsDTO.acessLink());
        }
        if (updatePublishDetailsDTO.photoLink()!= null){
            publish.setPhotoLink(updatePublishDetailsDTO.photoLink());
        }
        if (updatePublishDetailsDTO.initialDate()!= null){
            publish.setInitialDate(updatePublishDetailsDTO.initialDate());
        }
        if (updatePublishDetailsDTO.finalDate()!= null){
            publish.setFinalDate(updatePublishDetailsDTO.finalDate());
        }

        publishRepository.save(publish);
    }
}
